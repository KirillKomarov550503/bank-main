package com.netcracker.komarov.controllers.controller;

import com.netcracker.komarov.services.dto.RequestStatus;
import com.netcracker.komarov.services.dto.entity.RequestDTO;
import com.netcracker.komarov.services.exception.LogicException;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.interfaces.AccountService;
import com.netcracker.komarov.services.interfaces.CardService;
import com.netcracker.komarov.services.interfaces.ClientService;
import com.netcracker.komarov.services.interfaces.RequestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/bank/v1")
public class RequestController {
    private CardService cardService;
    private RequestService requestService;
    private ClientService clientService;
    private AccountService accountService;

    @Autowired
    public RequestController(RequestService requestService, ClientService clientService,
                             AccountService accountService, CardService cardService) {
        this.requestService = requestService;
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
            if (accountService.contain(clientId, accountId)) {
                RequestDTO dto = requestService.saveRequest(accountId, RequestStatus.ACCOUNT);
                responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(dto);
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

    @ApiOperation(value = "Sending request to unlock card")
    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}/cards/{cardId}/requests", method = RequestMethod.PATCH)
    public ResponseEntity sendRequestToUnlockCard(@PathVariable long clientId,
                                                  @PathVariable long accountId,
                                                  @PathVariable long cardId) {
        ResponseEntity responseEntity;
        try {
            clientService.findById(clientId);
            if (accountService.contain(clientId, accountId)) {
                if (cardService.contain(accountId, cardId)) {
                    RequestDTO dto = requestService.saveRequest(cardId, RequestStatus.CARD);
                    responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(dto);
                } else {
                    throw new LogicException("Account do not contain this card");
                }
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

    @ApiOperation(value = "Selecting all unlock requests")
    @RequestMapping(value = "/admins/requests/accounts", method = RequestMethod.GET)
    public ResponseEntity getAllRequests() {
        Collection<RequestDTO> dtos = requestService.findAllRequests();
        ResponseEntity responseEntity;
        if (dtos == null) {
            responseEntity = internalServerError("Server error");
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(dtos);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Deleting request by ID")
    @RequestMapping(value = "/admins/requests/{requestId}/del", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable long requestId) {
        ResponseEntity responseEntity;
        try {
            requestService.delete(requestId);
            responseEntity = ResponseEntity.status(HttpStatus.OK).build();
        } catch (NotFoundException e) {
            responseEntity = notFound(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting request by ID")
    @RequestMapping(value = "/admins/requests/{requestId}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable long requestId) {
        ResponseEntity responseEntity;
        try {
            RequestDTO dto = requestService.findById(requestId);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (NotFoundException e) {
            responseEntity = notFound(e.getMessage());
        }
        return responseEntity;
    }

    private ResponseEntity notFound(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    private ResponseEntity internalServerError(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}
