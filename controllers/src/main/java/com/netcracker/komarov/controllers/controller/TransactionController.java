package com.netcracker.komarov.controllers.controller;

import com.google.gson.Gson;
import com.netcracker.komarov.services.dto.entity.TransactionDTO;
import com.netcracker.komarov.services.exception.TransactionException;
import com.netcracker.komarov.services.interfaces.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("bank/v1")
public class TransactionController {
    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @RequestMapping(value = "/clients/{clientId}/transactions", method = RequestMethod.POST)
    public ResponseEntity createTransaction(@RequestBody TransactionDTO transactionDTO, @PathVariable long clientId) {
        Gson gson = new Gson();
        ResponseEntity responseEntity = null;
        TransactionDTO dto = null;
        try {
            dto = transactionService.createTransaction(transactionDTO, clientId);
        } catch (TransactionException e) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(gson.toJson(e.getMessage()));
        }
        if (dto != null) {
            responseEntity = ResponseEntity.status(HttpStatus.CREATED)
                    .body(gson.toJson(dto));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/clients/{clientId}/transactions", method = RequestMethod.GET)
    public ResponseEntity showTransactionStory(@PathVariable long clientId) {
        Collection<TransactionDTO> dtos = transactionService.showStories(clientId);
        Gson gson = new Gson();
        ResponseEntity responseEntity;
        if (dtos == null) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(gson.toJson("Server error"));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(gson.toJson(dtos.isEmpty() ? "You haven't transactions yet" : dtos));
        }
        return responseEntity;
    }
}
