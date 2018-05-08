package com.netcracker.komarov.controllers.controller;

import com.google.gson.Gson;
import com.netcracker.komarov.services.dto.entity.AccountDTO;
import com.netcracker.komarov.services.dto.entity.ClientDTO;
import com.netcracker.komarov.services.interfaces.AccountService;
import com.netcracker.komarov.services.interfaces.ClientService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "bank/v1")
public class AccountController {
    private AccountService accountService;

    private ClientService clientService;

    @Autowired
    public AccountController(AccountService accountService,
                             ClientService clientService) {
        this.accountService = accountService;
        this.clientService = clientService;
    }

    @ApiOperation(value = "Creation of new account")
    @RequestMapping(value = "/clients/{clientId}/accounts", method = RequestMethod.POST)
    public ResponseEntity create(@PathVariable long clientId) {
        Gson gson = new Gson();
        ResponseEntity responseEntity;
        ClientDTO clientDTO = clientService.findById(clientId);
        if (clientDTO == null) {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(gson.toJson("No such client in database"));
        } else {
            AccountDTO dto = accountService.createAccount(new AccountDTO(false, 0), clientId);
            if (dto == null) {
                responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(gson.toJson("Server error"));
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(gson.toJson(dto));
            }
        }
        return responseEntity;
    }

    @ApiOperation(value = "Unlocking account by ID")
    @RequestMapping(value = "/admins/requests/accounts/{accountId}", method = RequestMethod.PATCH)
    public ResponseEntity unlock(@PathVariable long accountId) {
        Gson gson = new Gson();
        AccountDTO accountDTO = accountService.findById(accountId);
        ResponseEntity responseEntity;
        if (accountDTO == null) {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(gson.toJson("No such admin in database"));
        } else {
            AccountDTO dto = accountService.unlockAccount(accountId);
            if (dto == null) {
                responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(gson.toJson("Server error"));
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.OK)
                        .body(gson.toJson(dto));
            }
        }
        return responseEntity;
    }

    @ApiOperation(value = "Locking account by ID")
    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}", method = RequestMethod.PATCH)
    public ResponseEntity lock(@PathVariable long clientId, @PathVariable long accountId) {
        Gson gson = new Gson();
        ResponseEntity responseEntity;
        ClientDTO clientDTO = clientService.findById(clientId);
        if (clientDTO == null) {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(gson.toJson("No such client in database"));
        } else {
            AccountDTO dto = accountService.lockAccount(accountId);
            if (dto == null) {
                responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(gson.toJson("Server error"));
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.OK)
                        .body(gson.toJson(dto));
            }
        }
        return responseEntity;
    }

    @ApiOperation(value = "Refill of your account by ID")
    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}/money", method = RequestMethod.PATCH)
    public ResponseEntity refill(@PathVariable long clientId, @PathVariable long accountId) {
        Gson gson = new Gson();
        ResponseEntity responseEntity;
        ClientDTO clientDTO = clientService.findById(clientId);
        if (clientDTO == null) {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(gson.toJson("No such client in database"));
        } else {
            AccountDTO dto = accountService.refill(accountId);
            if (dto == null) {
                responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(gson.toJson("Server error"));
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.OK)
                        .body(gson.toJson(dto));
            }
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting of all your accounts by status")
    @RequestMapping(value = "/clients/{clientId}/accounts/status", method = RequestMethod.GET)
    public ResponseEntity getByClientIdAndLock(@PathVariable long clientId, @RequestParam(name = "lock",
            required = false, defaultValue = "false") boolean lock) {
        Gson gson = new Gson();
        ResponseEntity responseEntity;
        ClientDTO clientDTO = clientService.findById(clientId);
        if (clientDTO == null) {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(gson.toJson("No such client in database"));
        } else {
            Collection<AccountDTO> dtos = accountService.getAccountsByClientIdAndLock(clientId, lock);
            if (dtos == null) {
                responseEntity = ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson("Server error"));
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.OK)
                        .body(gson.toJson(dtos.isEmpty() ? "Empty list of accounts" : dtos));
            }
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting all accounts")
    @RequestMapping(value = "/admins/accounts", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        Gson gson = new Gson();
        Collection<AccountDTO> dtos = accountService.getAllAccounts();
        return ResponseEntity.status(HttpStatus.OK)
                .body(gson.toJson(dtos.isEmpty() ? "Empty list of accounts" : dtos));
    }

    @ApiOperation(value = "Deleting account by ID")
    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable long clientId, @PathVariable long accountId) {
        Gson gson = new Gson();
        ResponseEntity responseEntity;
        ClientDTO clientDTO = clientService.findById(clientId);
        if (clientDTO == null) {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(gson.toJson("No such client"));
        } else {
            AccountDTO accountDTO = accountService.findById(accountId);
            if (accountDTO == null) {
                responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(gson.toJson("No such account in database"));
            } else {
                accountService.deleteById(accountId);
                responseEntity = ResponseEntity.status(HttpStatus.OK)
                        .body(gson.toJson("Account was deleted"));
            }
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting account by ID")
    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable long clientId, @PathVariable long accountId) {
        Gson gson = new Gson();
        ResponseEntity responseEntity;
        ClientDTO clientDTO = clientService.findById(clientId);
        if (clientDTO == null) {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(gson.toJson("No such client in database"));
        } else {
            AccountDTO dto = accountService.findById(accountId);
            if (dto == null) {
                responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(gson.toJson("No such account in database"));
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.OK)
                        .body(gson.toJson(dto));
            }
        }
        return responseEntity;
    }
}
