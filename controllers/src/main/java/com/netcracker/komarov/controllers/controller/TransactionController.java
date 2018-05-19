package com.netcracker.komarov.controllers.controller;

import com.netcracker.komarov.services.dto.entity.TransactionDTO;
import com.netcracker.komarov.services.exception.LogicException;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.interfaces.ClientService;
import com.netcracker.komarov.services.interfaces.TransactionService;
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
    private ClientService clientService;

    @Autowired
    public TransactionController(TransactionService transactionService, ClientService clientService) {
        this.transactionService = transactionService;
        this.clientService = clientService;
    }

    @ApiOperation(value = "Creation of new transaction")
    @RequestMapping(value = "/clients/{clientId}/transactions", method = RequestMethod.POST)
    public ResponseEntity createTransaction(@RequestBody TransactionDTO requestTransactionDTO,
                                            @PathVariable long clientId) {
        ResponseEntity responseEntity;
        try {
            TransactionDTO dto = transactionService.createTransaction(requestTransactionDTO, clientId);
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (NotFoundException e) {
            responseEntity = notFound(e.getMessage());
        } catch (LogicException e) {
            responseEntity = internalServerError(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting all transaction by client ID")
    @RequestMapping(value = "/clients/{clientId}/transactions", method = RequestMethod.GET)
    public ResponseEntity showTransactionStory(@PathVariable long clientId) {
        ResponseEntity responseEntity;
        try {
            Collection<TransactionDTO> dtos = transactionService.showStories(clientId);
            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(dtos);
        } catch (NotFoundException e) {
            responseEntity = notFound(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting transaction by ID")
    @RequestMapping(value = "/clients/{clientId}/transactions/{transactionId}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable long clientId, @PathVariable long transactionId) {
        ResponseEntity responseEntity;
        try {
            clientService.findById(clientId);
            if (transactionService.contain(clientId, transactionId)) {
                TransactionDTO dto = transactionService.findById(transactionId);
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(dto);
            } else {
                responseEntity = internalServerError("Client do not contain this transaction");
            }
        } catch (NotFoundException e) {
            responseEntity = notFound(e.getMessage());
        } catch (LogicException e) {
            responseEntity = internalServerError(e.getMessage());
        }
        return responseEntity;
    }

    private ResponseEntity notFound(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    private ResponseEntity internalServerError(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}
