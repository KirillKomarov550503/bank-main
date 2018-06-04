package com.netcracker.komarov.controllers.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.komarov.services.dto.entity.AccountDTO;
import com.netcracker.komarov.services.exception.LogicException;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.interfaces.AccountService;
import com.netcracker.komarov.services.interfaces.PersonService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "/bank/v1")
public class AccountController {
    private AccountService accountService;
    private PersonService personService;
    private ObjectMapper objectMapper;
    private Environment environment;

    @Autowired
    public AccountController(AccountService accountService, PersonService personService,
                             ObjectMapper objectMapper, Environment environment) {
        this.accountService = accountService;
        this.personService = personService;
        this.objectMapper = objectMapper;
        this.environment = environment;
    }

    @ApiOperation(value = "Create new account")
    @RequestMapping(value = "/clients/{personId}/accounts", method = RequestMethod.POST)
    public ResponseEntity save(@PathVariable long personId) {
        ResponseEntity responseEntity;
        try {
            AccountDTO dto = accountService.saveAccount(new AccountDTO(false, 0.0), personId);
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (NotFoundException e) {
            responseEntity = getErrorResponse(HttpStatus.FORBIDDEN, environment.getProperty("http.forbidden"));
        }
        return responseEntity;
    }

    @ApiOperation(value = "Lock account by ID")
    @RequestMapping(value = "/clients/{personId}/accounts/{accountId}", method = RequestMethod.PATCH)
    public ResponseEntity lockAccount(@PathVariable long personId, @PathVariable long accountId) {
        ResponseEntity responseEntity;
        try {
            personService.findById(personId);
            if (accountService.isContain(personId, accountId)) {
                AccountDTO dto = accountService.lockAccount(accountId);
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(dto);
            } else {
                responseEntity = getErrorResponse(HttpStatus.FORBIDDEN, environment.getProperty("http.forbidden"));
            }
        } catch (NotFoundException e) {
            responseEntity = getErrorResponse(HttpStatus.FORBIDDEN, environment.getProperty("http.forbidden"));
        } catch (LogicException e) {
            responseEntity = getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Refill of your account by ID")
    @RequestMapping(value = "/clients/{personId}/accounts/{accountId}/money", method = RequestMethod.PATCH)
    public ResponseEntity refillAccount(@PathVariable long personId, @PathVariable long accountId) {
        ResponseEntity responseEntity;
        personService.findById(personId);
        try {
            if (accountService.isContain(personId, accountId)) {
                AccountDTO dto = accountService.refillAccount(accountId);
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(dto);
            } else {
                responseEntity = getErrorResponse(HttpStatus.FORBIDDEN, environment.getProperty("http.forbidden"));
            }
        } catch (NotFoundException e) {
            responseEntity = getErrorResponse(HttpStatus.FORBIDDEN, environment.getProperty("http.forbidden"));
        } catch (LogicException e) {
            responseEntity = getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting of all your accounts by status")
    @RequestMapping(value = "/clients/{personId}/accounts/status", method = RequestMethod.GET)
    public ResponseEntity findByClientIdAndLock(@PathVariable long personId, @RequestParam(name = "lockAccount",
            required = false, defaultValue = "false") boolean lock) {
        ResponseEntity responseEntity;
        try {
            Collection<AccountDTO> dtos = accountService.findAccountsByClientIdAndLock(personId, lock);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(dtos);
        } catch (NotFoundException e) {
            responseEntity = getErrorResponse(HttpStatus.FORBIDDEN, environment.getProperty("http.forbidden"));
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting all accounts")
    @RequestMapping(value = "/admins/accounts", method = RequestMethod.GET)
    public ResponseEntity findAllAccounts() {
        Collection<AccountDTO> dtos = accountService.findAllAdmins();
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @ApiOperation(value = "Deleting account by ID")
    @RequestMapping(value = "/clients/{personId}/accounts/{accountId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable long personId, @PathVariable long accountId) {
        ResponseEntity responseEntity;
        try {
            personService.findById(personId);
            if (accountService.isContain(personId, accountId)) {
                accountService.deleteById(accountId);
                responseEntity = ResponseEntity.status(HttpStatus.OK).build();
            } else {
                responseEntity = getErrorResponse(HttpStatus.FORBIDDEN, environment.getProperty("http.forbidden"));
            }
        } catch (NotFoundException e) {
            responseEntity = getErrorResponse(HttpStatus.FORBIDDEN, environment.getProperty("http.forbidden"));
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting account by ID")
    @RequestMapping(value = "/clients/{personId}/accounts/{accountId}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable long personId, @PathVariable long accountId) {
        ResponseEntity responseEntity;
        try {
            personService.findById(personId);
            if (accountService.isContain(personId, accountId)) {
                AccountDTO dto = accountService.findById(accountId);
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(dto);
            } else {
                responseEntity = getErrorResponse(HttpStatus.FORBIDDEN, environment.getProperty("http.forbidden"));
            }
        } catch (NotFoundException e) {
            responseEntity = getErrorResponse(HttpStatus.FORBIDDEN, environment.getProperty("http.forbidden"));
        }
        return responseEntity;
    }

    private ResponseEntity getErrorResponse(HttpStatus httpStatus, String message) {
        return ResponseEntity.status(httpStatus).body(objectMapper.valueToTree(message));
    }
}
