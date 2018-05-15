package com.netcracker.komarov.controllers.controller;

import com.google.gson.Gson;
import com.netcracker.komarov.services.dto.entity.AccountDTO;
import com.netcracker.komarov.services.exception.LogicException;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.interfaces.AccountService;
import com.netcracker.komarov.services.interfaces.ClientService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.util.Collection;

@RestController
@RequestMapping(value = "/bank/v1")
public class AccountController {
    private AccountService accountService;
    private Gson gson;
    private ClientService clientService;

    @Autowired
    public AccountController(AccountService accountService,
                             ClientService clientService, Gson gson) {
        this.accountService = accountService;
        this.clientService = clientService;
        this.gson = gson;
    }

    @ApiOperation(value = "Creation of new account")
    @RequestMapping(value = "/clients/{clientId}/accounts", method = RequestMethod.POST)
    public ResponseEntity create(@PathVariable long clientId) {
        ResponseEntity responseEntity;
        try {
            AccountDTO dto = accountService.createAccount(new AccountDTO(false, 0), clientId);
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(gson.toJson(dto));
        } catch (NotFoundException e) {
            responseEntity = notFound(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Unlocking account by ID")
    @RequestMapping(value = "/admins/requests/accounts/{accountId}", method = RequestMethod.PATCH)
    public ResponseEntity unlock(@PathVariable long accountId) {
        ResponseEntity responseEntity;
        try {
            AccountDTO dto = accountService.unlockAccount(accountId);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(gson.toJson(dto));
        } catch (NotFoundException e) {
            responseEntity = notFound(e.getMessage());
        } catch (LogicException e) {
            responseEntity = internalServerError(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Locking account by ID")
    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}", method = RequestMethod.PATCH)
    public ResponseEntity lock(@PathVariable long clientId, @PathVariable long accountId) {
        ResponseEntity responseEntity;
        try {
            clientService.findById(clientId);
            if (accountService.contain(clientId, accountId)) {
                AccountDTO dto = accountService.lockAccount(accountId);
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(gson.toJson(dto));
            } else {
                throw new LogicException("Client do not contain this account");
            }
        } catch (NotFoundException e) {
            responseEntity = notFound(e.getMessage());
        } catch (LogicException e) {
            responseEntity = internalServerError(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Refill of your account by ID")
    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}/money", method = RequestMethod.PATCH)
    public ResponseEntity refill(@PathVariable long clientId, @PathVariable long accountId) {
        ResponseEntity responseEntity;
        clientService.findById(clientId);
        try {
            if (accountService.contain(clientId, accountId)) {
                AccountDTO dto = accountService.refill(accountId);
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(gson.toJson(dto));
            } else {
                throw new LogicException("Client do not contain this account");
            }
        } catch (NotFoundException e) {
            responseEntity = notFound(e.getMessage());
        } catch (LogicException e) {
            responseEntity = internalServerError(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting of all your accounts by status")
    @RequestMapping(value = "/clients/{clientId}/accounts/status", method = RequestMethod.GET)
    public ResponseEntity getByClientIdAndLock(@PathVariable long clientId, @RequestParam(name = "lock",
            required = false, defaultValue = "false") boolean lock) {
        ResponseEntity responseEntity;
        try {
            Collection<AccountDTO> dtos = accountService.getAccountsByClientIdAndLock(clientId, lock);
            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(gson.toJson(dtos.isEmpty() ? "Empty list of accounts" : dtos));
        } catch (NotFoundException e) {
            responseEntity = notFound(e.getMessage());
        } catch (LogicException e) {
            responseEntity = internalServerError(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting all accounts")
    @RequestMapping(value = "/admins/accounts", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        Collection<AccountDTO> dtos = accountService.getAllAccounts();
        return ResponseEntity.status(HttpStatus.OK)
                .body(gson.toJson(dtos.isEmpty() ? "Empty list of accounts" : dtos));
    }

    @ApiOperation(value = "Deleting account by ID")
    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable long clientId, @PathVariable long accountId) {
        ResponseEntity responseEntity;
        try {
            clientService.findById(clientId);
            if (accountService.contain(clientId, accountId)) {
                accountService.deleteById(accountId);
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(gson.toJson("Account was deleted"));
            } else {
                throw new LogicException("Client do not contain this account");
            }
        } catch (NotFoundException e) {
            responseEntity = notFound(e.getMessage());
        } catch (LogicException e) {
            responseEntity = internalServerError(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting account by ID")
    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable long clientId, @PathVariable long accountId) {
        ResponseEntity responseEntity;
        try {
            clientService.findById(clientId);
            if (accountService.contain(clientId, accountId)) {
                AccountDTO dto = accountService.findById(accountId);
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(gson.toJson(dto));
            } else {
                throw new LogicException("Client do not contain this account");
            }
        } catch (NotFoundException e) {
            responseEntity = notFound(e.getMessage());
        } catch (LogicException e) {
            responseEntity = internalServerError(e.getMessage());
        }
        return responseEntity;
    }

    private ResponseEntity notFound(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gson.toJson(message));
    }

    private ResponseEntity internalServerError(String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson(message));
    }
}
