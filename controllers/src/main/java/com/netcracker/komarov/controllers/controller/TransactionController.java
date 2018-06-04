package com.netcracker.komarov.controllers.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.komarov.services.dto.entity.TransactionDTO;
import com.netcracker.komarov.services.exception.LogicException;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.exception.ValidationException;
import com.netcracker.komarov.services.interfaces.PersonService;
import com.netcracker.komarov.services.interfaces.TransactionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/bank/v1")
public class TransactionController {
    private TransactionService transactionService;
    private PersonService personService;
    private ObjectMapper objectMapper;
    private Environment environment;

    @Autowired
    public TransactionController(TransactionService transactionService, PersonService personService,
                                 ObjectMapper objectMapper, Environment environment) {
        this.transactionService = transactionService;
        this.personService = personService;
        this.objectMapper = objectMapper;
        this.environment = environment;
    }

    @ApiOperation(value = "Create new transaction")
    @RequestMapping(value = "/clients/{personId}/transactions", method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody TransactionDTO transactionDTO, @PathVariable long personId) {
        ResponseEntity responseEntity;
        try {
            TransactionDTO dto = transactionService.save(transactionDTO, personId);
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (NotFoundException e) {
            responseEntity = getErrorResponse(HttpStatus.FORBIDDEN, environment.getProperty("http.forbidden"));
        } catch (LogicException | ValidationException e) {
            responseEntity = getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Select all transactions by client ID")
    @RequestMapping(value = "/clients/{personId}/transactions", method = RequestMethod.GET)
    public ResponseEntity findTransactionsByClientId(@PathVariable long personId) {
        ResponseEntity responseEntity;
        try {
            Collection<TransactionDTO> dtos = transactionService.findTransactionsByClientId(personId);
            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(dtos);
        } catch (NotFoundException e) {
            responseEntity = getErrorResponse(HttpStatus.FORBIDDEN, environment.getProperty("http.forbidden"));
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecte transaction by ID")
    @RequestMapping(value = "/clients/{personId}/transactions/{transactionId}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable long personId, @PathVariable long transactionId) {
        ResponseEntity responseEntity;
        try {
            personService.findById(personId);
            if (transactionService.isContain(personId, transactionId)) {
                TransactionDTO dto = transactionService.findById(transactionId);
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(dto);
            } else {
                responseEntity = getErrorResponse(HttpStatus.FORBIDDEN, environment.getProperty("http.forbidden"));
            }
        } catch (NotFoundException e) {
            responseEntity = getErrorResponse(HttpStatus.FORBIDDEN, environment.getProperty("http.forbidden"));
        } catch (LogicException e) {
            responseEntity = getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return responseEntity;
    }

    private ResponseEntity getErrorResponse(HttpStatus httpStatus, String message) {
        return ResponseEntity.status(httpStatus).body(objectMapper.valueToTree(message));
    }
}
