package com.netcracker.komarov.controllers.controller;

import com.google.gson.Gson;
import com.netcracker.komarov.services.dto.entity.AccountDTO;
import com.netcracker.komarov.services.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "bank/v1")
public class AccountController {
    private AccountService accountService;


    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/clients/{clientId}/accounts", method = RequestMethod.POST)
    public ResponseEntity create(@PathVariable long clientId) {
        Gson gson = new Gson();
        AccountDTO dto = accountService.createAccount(new AccountDTO(false, 0), clientId);
        ResponseEntity responseEntity;
        if (dto == null) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(gson.toJson("Server error"));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(gson.toJson(dto));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/admins/requests/accounts/{accountId}", method = RequestMethod.PATCH)
    public ResponseEntity unlock(@PathVariable long accountId) {
        Gson gson = new Gson();
        AccountDTO dto = accountService.unlockAccount(accountId);
        ResponseEntity responseEntity;
        if (dto == null) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(gson.toJson("Server error"));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(gson.toJson(dto));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}", method = RequestMethod.PATCH)
    public ResponseEntity lock(@PathVariable long clientId, @PathVariable long accountId) {
        Gson gson = new Gson();
        AccountDTO dto = accountService.lockAccount(accountId);
        ResponseEntity responseEntity;
        if (dto == null) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(gson.toJson("Server error"));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(gson.toJson(dto));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}/money", method = RequestMethod.PATCH)
    public ResponseEntity refill(@PathVariable long clientId, @PathVariable long accountId) {
        Gson gson = new Gson();
        AccountDTO dto = accountService.refill(accountId);
        ResponseEntity responseEntity;
        if (dto == null) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(gson.toJson("Server error"));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(gson.toJson(dto));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/clients/{clientId}/accounts/status", method = RequestMethod.GET)
    public ResponseEntity getByClientIdAndLock(@PathVariable long clientId, @RequestParam(name = "lock",
            required = false, defaultValue = "false") boolean lock) {
        Gson gson = new Gson();
        Collection<AccountDTO> dtos = accountService.getAccountsByClientIdAndLock(clientId, lock);
        ResponseEntity responseEntity;
        if (dtos == null) {
            responseEntity = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson("Server error"));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(gson.toJson(dtos.isEmpty() ? "Empty list of accounts" : dtos));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/admins/accounts", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        Gson gson = new Gson();
        Collection<AccountDTO> dtos = accountService.getAllAccounts();
        return ResponseEntity.status(HttpStatus.OK)
                .body(gson.toJson(dtos.isEmpty() ? "Empty list of accounts" : dtos));
    }

    @RequestMapping(value = "/accounts/{accountId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable long accountId) {
        accountService.deleteById(accountId);
        Gson gson = new Gson();
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson("Account was deleted"));
    }
}
