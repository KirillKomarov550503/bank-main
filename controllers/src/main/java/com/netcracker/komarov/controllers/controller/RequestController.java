package com.netcracker.komarov.controllers.controller;

import com.netcracker.komarov.services.dto.Status;
import com.netcracker.komarov.services.dto.entity.RequestDTO;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.feign.RequestFeignClient;
import com.netcracker.komarov.services.interfaces.AccountService;
import com.netcracker.komarov.services.interfaces.CardService;
import com.netcracker.komarov.services.interfaces.ClientService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

@RestController
@RequestMapping("/bank/v1")
public class RequestController {
    private CardService cardService;
    private ClientService clientService;
    private AccountService accountService;
    private RequestFeignClient requestFeignClient;

    @Autowired
    public RequestController(RequestFeignClient requestFeignClient, ClientService clientService,
                             AccountService accountService, CardService cardService) {
        this.requestFeignClient = requestFeignClient;
        this.clientService = clientService;
        this.accountService = accountService;
        this.cardService = cardService;
    }

    @ApiOperation(value = "Sending request to unlock account")
    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}/requests", method = RequestMethod.PATCH)
    public ResponseEntity sendRequestToUnlockAccount(@PathVariable long clientId,
                                                     @PathVariable long accountId) {
        ResponseEntity responseEntity;
        try {
            clientService.findById(clientId);
            if (accountService.isContain(clientId, accountId)) {
                if (accountService.findById(accountId).isLocked()) {
                    responseEntity = requestFeignClient.save(new RequestDTO(accountId, Status.ACCOUNT));
                } else {
                    responseEntity = getInternalServerErrorResponseEntity("Account is unlocking now");
                }
            } else {
                responseEntity = getInternalServerErrorResponseEntity("Client do not contain this account");
            }
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        } catch (HttpStatusCodeException e) {
            responseEntity = getExceptionFromRequestService(e);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Sending request to unlock card")
    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}/cards/{cardId}/requests", method = RequestMethod.PATCH)
    public ResponseEntity sendRequestToUnlockCard(@PathVariable long clientId,
                                                  @PathVariable long accountId,
                                                  @PathVariable long cardId) {
        ResponseEntity responseEntity;
        try {
            clientService.findById(clientId);
            if (accountService.isContain(clientId, accountId)) {
                if (cardService.isContain(accountId, cardId)) {
                    if (cardService.findById(cardId).isLocked()) {
                        responseEntity = requestFeignClient.save(new RequestDTO(cardId, Status.CARD));
                    } else {
                        responseEntity = getInternalServerErrorResponseEntity("Card is unlocking now");
                    }
                } else {
                    responseEntity = getInternalServerErrorResponseEntity("Account do not contain this card");
                }
            } else {
                responseEntity = getInternalServerErrorResponseEntity("Client do not contain this account");
            }
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        } catch (HttpStatusCodeException e) {
            responseEntity = getExceptionFromRequestService(e);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting all unlock requests")
    @RequestMapping(value = "/admins/requests/accounts", method = RequestMethod.GET)
    public ResponseEntity getAllRequests() {
        return requestFeignClient.findAllRequests();
    }

    @ApiOperation(value = "Deleting request by ID")
    @RequestMapping(value = "/admins/requests/{requestId}/del", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable long requestId) {
        ResponseEntity responseEntity;
        try {
            responseEntity = requestFeignClient.deleteById(requestId);
        } catch (HttpStatusCodeException e) {
            responseEntity = getExceptionFromRequestService(e);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting request by ID")
    @RequestMapping(value = "/admins/requests/{requestId}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable long requestId) {
        ResponseEntity responseEntity;
        try {
            responseEntity = requestFeignClient.findById(requestId);
        } catch (HttpStatusCodeException e) {
            responseEntity = getExceptionFromRequestService(e);
        }
        return responseEntity;
    }

    private ResponseEntity getExceptionFromRequestService(HttpStatusCodeException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    }

    private ResponseEntity getNotFoundResponseEntity(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    private ResponseEntity getInternalServerErrorResponseEntity(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}
