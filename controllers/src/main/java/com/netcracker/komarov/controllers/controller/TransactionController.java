package com.netcracker.komarov.controllers.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.komarov.services.dto.entity.TransactionDTO;
import com.netcracker.komarov.services.exception.LogicException;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.exception.ValidationException;
import com.netcracker.komarov.services.interfaces.PersonService;
import com.netcracker.komarov.services.interfaces.TransactionService;
import com.netcracker.komarov.services.validator.impl.TransactionValidator;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/bank/v1")
public class TransactionController {
    private TransactionService transactionService;
    private PersonService personService;
    private TransactionValidator transactionValidator;
    private ObjectMapper objectMapper;

    @Autowired
    public TransactionController(TransactionService transactionService, PersonService personService,
                                 TransactionValidator transactionValidator, ObjectMapper objectMapper) {
        this.transactionService = transactionService;
        this.personService = personService;
        this.transactionValidator = transactionValidator;
        this.objectMapper = objectMapper;
    }

    @ApiOperation(value = "Creation of new transaction")
    @RequestMapping(value = "/clients/{personId}/transactions", method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody TransactionDTO transactionDTO, @PathVariable long personId) {
        ResponseEntity responseEntity;
        try {
            transactionValidator.validate(transactionDTO);
            TransactionDTO dto = transactionService.save(transactionDTO, personId);
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        } catch (LogicException e) {
            responseEntity = getInternalServerErrorResponseEntity(e.getMessage());
        } catch (ValidationException e) {
            responseEntity = getBadRequestResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting all transaction by client ID")
    @RequestMapping(value = "/clients/{personId}/transactions", method = RequestMethod.GET)
    public ResponseEntity findTransactionsByClientId(@PathVariable long personId) {
        ResponseEntity responseEntity;
        try {
            Collection<TransactionDTO> dtos = transactionService.findTransactionsByClientId(personId);
            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(dtos);
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting transaction by ID")
    @RequestMapping(value = "/clients/{personId}/transactions/{transactionId}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable long personId, @PathVariable long transactionId) {
        ResponseEntity responseEntity;
        try {
            personService.findById(personId);
            if (transactionService.isContain(personId, transactionId)) {
                TransactionDTO dto = transactionService.findById(transactionId);
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(dto);
            } else {
                responseEntity = getInternalServerErrorResponseEntity("Client do not contain this transaction");
            }
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        } catch (LogicException e) {
            responseEntity = getInternalServerErrorResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    private ResponseEntity getNotFoundResponseEntity(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(objectMapper.valueToTree(message));
    }

    private ResponseEntity getInternalServerErrorResponseEntity(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(objectMapper.valueToTree(message));
    }

    private ResponseEntity getBadRequestResponseEntity(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(objectMapper.valueToTree(message));
    }
}
