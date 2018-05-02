package com.netcracker.komarov.controllers.controller;

import com.netcracker.komarov.controllers.exception.DataException;
import com.netcracker.komarov.controllers.exception.ServerException;
import com.netcracker.komarov.services.dto.entity.TransactionDTO;
import com.netcracker.komarov.services.exception.TransactionException;
import com.netcracker.komarov.services.interfaces.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class TransactionController {
    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/clients/{clientId}/transactions", method = RequestMethod.POST)
    public TransactionDTO createTransaction(@RequestBody TransactionDTO transactionDTO, @PathVariable long clientId) {
        TransactionDTO dto;
        try {
            dto = transactionService.createTransaction(transactionDTO, clientId);
        } catch (TransactionException e) {
            throw new DataException("Transaction amount is greater than your balance");
        }
        if (dto == null) {
            throw new NullPointerException("client");
        }
        return dto;
    }

    @ResponseStatus
    @ExceptionHandler(value = DataException.class)
    public String handleDataException(DataException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NullPointerException.class)
    public String handleNullPointerException(NullPointerException ex) {
        return "Not found this " + ex.getMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = ServerException.class)
    public String handleServerException(ServerException ex) {
        return ex.getMessage();
    }
}
