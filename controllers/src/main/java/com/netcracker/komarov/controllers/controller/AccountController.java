package com.netcracker.komarov.controllers.controller;

import com.netcracker.komarov.services.dto.entity.AccountDTO;
import com.netcracker.komarov.services.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "api/v1")
public class AccountController {
    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/clients/{clientId}/accounts", method = RequestMethod.POST)
    public ResponseEntity create(@PathVariable long clientId) {
        AccountDTO dto = accountService.createAccount(new AccountDTO(false, 0), clientId);
        ResponseEntity responseEntity;
        if (dto == null) {
            responseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            responseEntity = new ResponseEntity(dto, HttpStatus.CREATED);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/admins/{adminId}/requests/accounts/{accountId}/unbloking", method = RequestMethod.GET)
    public ResponseEntity unlock(@PathVariable long adminId, @PathVariable long accountId) {
        AccountDTO dto = accountService.unlockAccount(accountId);
        ResponseEntity responseEntity;
        if (dto == null) {
            responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        } else if (dto.isLocked()) {
            responseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            responseEntity = new ResponseEntity(dto, HttpStatus.OK);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}/blocking", method = RequestMethod.GET)
    public ResponseEntity lock(@PathVariable long clientId, @PathVariable long accountId) {
        AccountDTO dto = accountService.lockAccount(accountId);
        ResponseEntity responseEntity;
        if (dto == null) {
            responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        } else if (dto.isLocked()) {
            responseEntity = new ResponseEntity(dto, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}/money", method = RequestMethod.GET)
    public ResponseEntity refill(@PathVariable long clientId, @PathVariable long accountId) {
        AccountDTO dto = accountService.refill(accountId);
        ResponseEntity responseEntity;
        if (dto == null) {
            responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            responseEntity = new ResponseEntity(dto, HttpStatus.OK);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/clients/{clientId}/accounts/status", method = RequestMethod.GET)
    public ResponseEntity getByClientIdAndLock(@PathVariable long clientId, @RequestParam(name = "lock",
            required = false, defaultValue = "false") boolean lock) {
        ResponseEntity responseEntity;
        Collection<AccountDTO> dtos = accountService.getAccountsByClientIdAndLock(clientId, lock);
        if (dtos == null) {
            responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            responseEntity = new ResponseEntity(dtos, HttpStatus.OK);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/admins/{adminId}/accounts", method = RequestMethod.GET)
    public Collection<AccountDTO> getAll(@PathVariable long adminId) {
        return accountService.getAllAccounts();
    }

    @RequestMapping(value = "/admins/{adminId}/requests/accounts", method = RequestMethod.GET)
    public Collection<AccountDTO> getAllRequests(@PathVariable long adminId) {
        return accountService.getAllUnlockAccountRequest();
    }
}
