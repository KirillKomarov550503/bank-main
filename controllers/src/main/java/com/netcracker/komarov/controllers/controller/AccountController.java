package com.netcracker.komarov.controllers.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.komarov.services.dto.entity.AccountDTO;
import com.netcracker.komarov.services.exception.LogicException;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.interfaces.AccountService;
import com.netcracker.komarov.services.interfaces.PersonService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public AccountController(AccountService accountService, PersonService personService, ObjectMapper objectMapper) {
        this.accountService = accountService;
        this.personService = personService;
        this.objectMapper = objectMapper;
    }

    @ApiOperation(value = "Creation of new account")
    @RequestMapping(value = "/clients/{personId}/accounts", method = RequestMethod.POST)
    public ResponseEntity save(@PathVariable long personId) {
        ResponseEntity responseEntity;
        try {
            AccountDTO dto = accountService.saveAccount(new AccountDTO(false, 0.0), personId);
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Unlocking account by ID")
    @RequestMapping(value = "/admins/requests/accounts/{accountId}", method = RequestMethod.PATCH)
    public ResponseEntity unlockAccount(@PathVariable long accountId) {
        ResponseEntity responseEntity;
        try {
            AccountDTO dto = accountService.unlockAccount(accountId);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        } catch (LogicException e) {
            responseEntity = getInternalServerErrorResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Locking account by ID")
    @RequestMapping(value = "/clients/{personId}/accounts/{accountId}", method = RequestMethod.PATCH)
    public ResponseEntity lockAccount(@PathVariable long personId, @PathVariable long accountId) {
        ResponseEntity responseEntity;
        try {
            personService.findById(personId);
            if (accountService.isContain(personId, accountId)) {
                AccountDTO dto = accountService.lockAccount(accountId);
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(dto);
            } else {
                responseEntity = getInternalServerErrorResponseEntity("Client do not contain this account");
            }
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        } catch (LogicException e) {
            responseEntity = getInternalServerErrorResponseEntity(e.getMessage());
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
                responseEntity = getInternalServerErrorResponseEntity("Client do not contain this account");
            }
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        } catch (LogicException e) {
            responseEntity = getInternalServerErrorResponseEntity(e.getMessage());
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
            responseEntity = getNotFoundResponseEntity(e.getMessage());
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
                responseEntity = getInternalServerErrorResponseEntity("Client do not contain this account");
            }
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
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
                responseEntity = getInternalServerErrorResponseEntity("Client do not contain this account");
            }
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    private ResponseEntity getNotFoundResponseEntity(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(objectMapper.valueToTree(message));
    }

    private ResponseEntity getInternalServerErrorResponseEntity(String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(objectMapper.valueToTree(message));
    }
}
