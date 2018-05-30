package com.netcracker.komarov.controllers.controller;

import com.netcracker.komarov.services.dto.Status;
import com.netcracker.komarov.services.dto.entity.RequestDTO;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.feign.RequestFeignClient;
import com.netcracker.komarov.services.interfaces.AccountService;
import com.netcracker.komarov.services.interfaces.CardService;
import com.netcracker.komarov.services.interfaces.PersonService;
import feign.FeignException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/bank/v1")
public class RequestController {
    private CardService cardService;
    private PersonService personService;
    private AccountService accountService;
    private RequestFeignClient requestFeignClient;

    @Autowired
    public RequestController(RequestFeignClient requestFeignClient, PersonService personService,
                             AccountService accountService, CardService cardService) {
        this.requestFeignClient = requestFeignClient;
        this.personService = personService;
        this.accountService = accountService;
        this.cardService = cardService;
    }

    @ApiOperation(value = "Sending request to unlockAccount account")
    @RequestMapping(value = "/clients/{personId}/accounts/{accountId}/requests", method = RequestMethod.PATCH)
    public ResponseEntity sendRequestToUnlockAccount(@PathVariable long personId,
                                                     @PathVariable long accountId) {
        ResponseEntity responseEntity;
        try {
            personService.findById(personId);
            if (accountService.isContain(personId, accountId)) {
                if (accountService.findById(accountId).isLocked()) {
                    responseEntity = requestFeignClient.save(new RequestDTO(accountId, Status.ACCOUNT));
                } else {
                    responseEntity = getResponseEntityOfInternalServerError("Account is unlocking now");
                }
            } else {
                responseEntity = getResponseEntityOfInternalServerError("Client do not contain this account");
            }
        } catch (NotFoundException e) {
            responseEntity = getResponseEntityOfNotFoundException(e.getMessage());
        } catch (FeignException e) {
            responseEntity = getResponseEntityOfFeignException(e);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Sending request to unlockAccount card")
    @RequestMapping(value = "/clients/{personId}/accounts/{accountId}/cards/{cardId}/requests",
            method = RequestMethod.PATCH)
    public ResponseEntity sendRequestToUnlockCard(@PathVariable long personId,
                                                  @PathVariable long accountId,
                                                  @PathVariable long cardId) {
        ResponseEntity responseEntity;
        try {
            personService.findById(personId);
            if (accountService.isContain(personId, accountId)) {
                if (cardService.isContain(accountId, cardId)) {
                    if (cardService.findById(cardId).isLocked()) {
                        responseEntity = requestFeignClient.save(new RequestDTO(cardId, Status.CARD));
                    } else {
                        responseEntity = getResponseEntityOfInternalServerError("Card is unlocking now");
                    }
                } else {
                    responseEntity = getResponseEntityOfInternalServerError("Account do not contain this card");
                }
            } else {
                responseEntity = getResponseEntityOfInternalServerError("Client do not contain this account");
            }
        } catch (NotFoundException e) {
            responseEntity = getResponseEntityOfNotFoundException(e.getMessage());
        } catch (FeignException e) {
            responseEntity = getResponseEntityOfFeignException(e);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting all unlockAccount requests")
    @RequestMapping(value = "/admins/requests/accounts", method = RequestMethod.GET)
    public ResponseEntity findAllRequests() {
        return requestFeignClient.findAllRequests();
    }

    @ApiOperation(value = "Deleting request by ID")
    @RequestMapping(value = "/admins/requests/{requestId}/del", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable long requestId) {
        ResponseEntity responseEntity;
        try {
            responseEntity = requestFeignClient.deleteById(requestId);
        } catch (FeignException e) {
            responseEntity = getResponseEntityOfFeignException(e);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting request by ID")
    @RequestMapping(value = "/admins/requests/{requestId}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable long requestId) {
        ResponseEntity responseEntity;
        try {
            responseEntity = requestFeignClient.findById(requestId);
        } catch (FeignException e) {
            responseEntity = getResponseEntityOfFeignException(e);
        }
        return responseEntity;
    }

    private ResponseEntity getResponseEntityOfFeignException(FeignException e) {
        return ResponseEntity.status(e.status()).body(
                Stream.of(e.getMessage().split("\n")).skip(1).collect(Collectors.toList())
        );
    }

    private ResponseEntity getResponseEntityOfNotFoundException(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    private ResponseEntity getResponseEntityOfInternalServerError(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}
