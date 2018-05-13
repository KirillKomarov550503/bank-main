package com.netcracker.komarov.controllers.controller;

import com.google.gson.Gson;
import com.netcracker.komarov.services.dto.RequestStatus;
import com.netcracker.komarov.services.dto.entity.AccountDTO;
import com.netcracker.komarov.services.dto.entity.CardDTO;
import com.netcracker.komarov.services.dto.entity.ClientDTO;
import com.netcracker.komarov.services.dto.entity.RequestDTO;
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
    private Gson gson;

    @Autowired
    public RequestController(RequestService requestService, ClientService clientService,
                             AccountService accountService, CardService cardService, Gson gson) {
        this.requestService = requestService;
        this.clientService = clientService;
        this.accountService = accountService;
        this.cardService = cardService;
        this.gson = gson;
    }

    @ApiOperation(value = "Sending request to unlock account")
    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}/requests", method = RequestMethod.PATCH)
    public ResponseEntity sendRequestToUnlockAccount(@PathVariable long clientId,
                                                     @PathVariable long accountId) {
        ClientDTO clientDTO = clientService.findById(clientId);
        ResponseEntity responseEntity;
        if (clientDTO == null) {
            responseEntity = notFound("No such client in database");
        } else {
            AccountDTO accountDTO = accountService.findById(accountId);
            if (accountDTO == null) {
                responseEntity = notFound("No such account in database");
            } else {
                RequestDTO dto = requestService.saveRequest(accountId, RequestStatus.ACCOUNT);
                if (dto == null) {
                    responseEntity = internalServerError("Server error");
                } else {
                    responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(gson.toJson(dto));
                }
            }
        }
        return responseEntity;
    }

    @ApiOperation(value = "Sending request to unlock card")
    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}/cards/{cardId}/requests", method = RequestMethod.PATCH)
    public ResponseEntity sendRequestToUnlockCard(@PathVariable long clientId,
                                                  @PathVariable long accountId,
                                                  @PathVariable long cardId) {
        ClientDTO clientDTO = clientService.findById(clientId);
        ResponseEntity responseEntity;
        if (clientDTO == null) {
            responseEntity = notFound("No such client in database");
        } else {
            AccountDTO accountDTO = accountService.findById(accountId);
            if (accountDTO == null) {
                responseEntity = notFound("No such account in database");
            } else {
                CardDTO cardDTO = cardService.findById(cardId);
                if (cardDTO == null) {
                    responseEntity = notFound("No such card in database");
                } else {
                    RequestDTO dto = requestService.saveRequest(cardId, RequestStatus.CARD);
                    if (dto == null) {
                        responseEntity = internalServerError("Server error");
                    } else {
                        responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(gson.toJson(dto));
                    }
                }
            }
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
                    .body(gson.toJson(dtos.isEmpty() ? "Empty list of requests" : dtos));
        }
        return responseEntity;
    }

    @ApiOperation(value = "Deleting request by ID")
    @RequestMapping(value = "/admins/requests/{requestId}/del", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable long requestId) {
        RequestDTO requestDTO = requestService.findById(requestId);
        ResponseEntity responseEntity;
        if (requestDTO == null) {
            responseEntity = notFound("No such request in database");
        } else {
            requestService.delete(requestId);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(gson.toJson("Request was deleted"));
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting request by ID")
    @RequestMapping(value = "/admins/requests/{requestId}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable long requestId) {
        ResponseEntity responseEntity;
        RequestDTO dto = requestService.findById(requestId);
        if (dto == null) {
            responseEntity = notFound("No such request in database");
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(gson.toJson(dto));
        }
        return responseEntity;
    }

    private ResponseEntity notFound(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gson.toJson(message));
    }

    private ResponseEntity internalServerError(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gson.toJson(message));
    }
}
