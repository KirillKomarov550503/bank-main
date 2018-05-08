package com.netcracker.komarov.controllers.controller;

import com.google.gson.Gson;
import com.netcracker.komarov.services.dto.entity.ClientDTO;
import com.netcracker.komarov.services.dto.entity.TransactionDTO;
import com.netcracker.komarov.services.exception.TransactionException;
import com.netcracker.komarov.services.interfaces.ClientService;
import com.netcracker.komarov.services.interfaces.TransactionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("bank/v1")
public class TransactionController {
    private TransactionService transactionService;
    private ClientService clientService;
    private Gson gson;

    @Autowired
    public TransactionController(TransactionService transactionService, ClientService clientService, Gson gson) {
        this.transactionService = transactionService;
        this.clientService = clientService;
        this.gson = gson;
    }

    @ApiOperation(value = "Creation of new transaction")
    @RequestMapping(value = "/clients/{clientId}/transactions", method = RequestMethod.POST)
    public ResponseEntity createTransaction(@RequestBody TransactionDTO requestTransactionDTO,
                                            @PathVariable long clientId) {
        ResponseEntity responseEntity = null;
        ClientDTO clientDTO = clientService.findById(clientId);
        if (clientDTO == null) {
            responseEntity = notFound("No such client in database");
        } else {
            TransactionDTO dto = null;
            try {
                dto = transactionService.createTransaction(requestTransactionDTO, clientId);
            } catch (TransactionException e) {
                responseEntity = internalServerError(e.getMessage());
            }
            if (dto != null) {
                responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(gson.toJson(dto));
            }
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting all transaction by client ID")
    @RequestMapping(value = "/clients/{clientId}/transactions", method = RequestMethod.GET)
    public ResponseEntity showTransactionStory(@PathVariable long clientId) {
        ClientDTO clientDTO = clientService.findById(clientId);
        ResponseEntity responseEntity;
        if (clientDTO == null) {
            responseEntity = notFound("No such client in database");
        } else {
            Collection<TransactionDTO> dtos = transactionService.showStories(clientId);
            if (dtos == null) {
                responseEntity = internalServerError("Server error");
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.OK)
                        .body(gson.toJson(dtos.isEmpty() ? "You haven't transactions yet" : dtos));
            }
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting transaction by ID")
    @RequestMapping(value = "/clients/{clientId}/transactions/{transactionId}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable long clientId, @PathVariable long transactionId) {
        ResponseEntity responseEntity;
        ClientDTO clientDTO = clientService.findById(clientId);
        if (clientDTO == null) {
            responseEntity = notFound("No such client in database");
        } else {
            TransactionDTO dto = transactionService.findById(transactionId);
            if (dto == null) {
                responseEntity = notFound("No such transaction in database");
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(gson.toJson(dto));
            }
        }
        return responseEntity;
    }

    private ResponseEntity notFound(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gson.toJson(message));
    }

    private ResponseEntity internalServerError(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gson.toJson(message));
    }
}
