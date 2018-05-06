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

    private Gson gson;

    @Autowired
    public AccountController(AccountService accountService, Gson gson) {
        this.accountService = accountService;
        this.gson = gson;
    }

    @RequestMapping(value = "/clients/{clientId}/accounts", method = RequestMethod.POST)
    public ResponseEntity create(@PathVariable long clientId) {
        AccountDTO dto = accountService.createAccount(new AccountDTO(false, 0), clientId);
        ResponseEntity responseEntity = ResponseEntity.
                status(dto == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.CREATED)
                .body(gson.toJson(dto));
        return responseEntity;
    }

    @RequestMapping(value = "/admins/requests/accounts/{accountId}", method = RequestMethod.PATCH)
    public ResponseEntity unlock(@PathVariable long accountId) {
        AccountDTO dto = accountService.unlockAccount(accountId);
        ResponseEntity responseEntity = ResponseEntity.
                status(dto == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK)
                .body(gson.toJson(dto));
        return responseEntity;
    }

    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}", method = RequestMethod.PATCH)
    public ResponseEntity lock(@PathVariable long clientId, @PathVariable long accountId) {
        AccountDTO dto = accountService.lockAccount(accountId);
        ResponseEntity responseEntity = ResponseEntity.
                status(dto == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK)
                .body(gson.toJson(dto));
        return responseEntity;
    }

    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}/money", method = RequestMethod.PATCH)
    public ResponseEntity refill(@PathVariable long clientId, @PathVariable long accountId) {
        AccountDTO dto = accountService.refill(accountId);
        ResponseEntity responseEntity = ResponseEntity.
                status(dto == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK)
                .body(gson.toJson(dto));
        return responseEntity;
    }

    @RequestMapping(value = "/clients/{clientId}/accounts/status", method = RequestMethod.GET)
    public ResponseEntity getByClientIdAndLock(@PathVariable long clientId, @RequestParam(name = "lock",
            required = false, defaultValue = "false") boolean lock) {
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
        Collection<AccountDTO> dtos = accountService.getAllAccounts();
        return ResponseEntity.status(HttpStatus.OK)
                .body(gson.toJson(dtos.isEmpty() ? "Empty list of accounts" : dtos));
    }

    @RequestMapping(value = "/admins/requests/accounts", method = RequestMethod.GET)
    public ResponseEntity getAllRequests() {
        Collection<AccountDTO> dtos = accountService.getAllRequests();
        return ResponseEntity.status(HttpStatus.OK)
                .body(gson.toJson(dtos.isEmpty() ? "Empty list of accounts" : dtos));
    }
}
