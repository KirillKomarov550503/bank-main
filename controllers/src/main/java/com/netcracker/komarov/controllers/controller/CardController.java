package com.netcracker.komarov.controllers.controller;

import com.netcracker.komarov.services.dto.entity.CardDTO;
import com.netcracker.komarov.services.exception.LogicException;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.interfaces.AccountService;
import com.netcracker.komarov.services.interfaces.CardService;
import com.netcracker.komarov.services.interfaces.ClientService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/bank/v1")
public class CardController {
    private CardService cardService;
    private ClientService clientService;
    private AccountService accountService;

    @Autowired
    public CardController(CardService cardService, ClientService clientService, AccountService accountService) {
        this.cardService = cardService;
        this.clientService = clientService;
        this.accountService = accountService;
    }

    @ApiOperation(value = "Creation of new card")
    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}/cards", method = RequestMethod.POST)
    public ResponseEntity save(@PathVariable long clientId, @PathVariable long accountId,
                               @RequestBody CardDTO requestCardDTO) {
        ResponseEntity responseEntity;
        try {
            clientService.findById(clientId);
            if (accountService.isContain(clientId, accountId)) {
                requestCardDTO.setAccountId(accountId);
                CardDTO dto = cardService.save(requestCardDTO);
                responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(dto);
            } else {
                responseEntity = getInternalServerErrorResponseEntity("Client do not contain this account");
            }
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Locking of card by ID")
    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}/cards/{cardId}", method = RequestMethod.PATCH)
    public ResponseEntity lockCard(@PathVariable long clientId, @PathVariable long accountId,
                                   @PathVariable long cardId) {
        ResponseEntity responseEntity;
        try {
            clientService.findById(clientId);
            if (accountService.isContain(clientId, accountId)) {
                if (cardService.isContain(accountId, cardId)) {
                    CardDTO dto = cardService.lockCard(cardId);
                    responseEntity = ResponseEntity.status(HttpStatus.OK).body(dto);
                } else {
                    responseEntity = getInternalServerErrorResponseEntity("Account do not contain this card");
                }
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

    @ApiOperation(value = "Unlocking card by ID")
    @RequestMapping(value = "/admins/requests/cards/{cardId}", method = RequestMethod.PATCH)
    public ResponseEntity unlockCard(@PathVariable long cardId) {
        ResponseEntity responseEntity;
        try {
            CardDTO dto = cardService.unlockCard(cardId);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        } catch (LogicException e) {
            responseEntity = getInternalServerErrorResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting all card by status")
    @RequestMapping(value = "/clients/{clientId}/cards/status", method = RequestMethod.GET)
    public ResponseEntity findByClientIdAndLock(@PathVariable long clientId, @RequestParam(name = "lockAccount",
            required = false, defaultValue = "false") boolean lock) {
        ResponseEntity responseEntity;
        try {
            Collection<CardDTO> dtos = cardService.findCardsByClientIdAndLock(clientId, lock);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(dtos);
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting all card that belong to account ID")
    @RequestMapping(value = "/clients/{clientId}/accounts{accountId}/cards", method = RequestMethod.GET)
    public ResponseEntity findByAccountId(@PathVariable long clientId, @PathVariable long accountId) {
        ResponseEntity responseEntity;
        try {
            clientService.findById(clientId);
            if (accountService.isContain(clientId, accountId)) {
                Collection<CardDTO> dtos = cardService.findCardsByAccountId(accountId);
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(dtos);
            } else {
                responseEntity = getInternalServerErrorResponseEntity("Client do not contain this account");
            }
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting all card")
    @RequestMapping(value = "/admins/cards", method = RequestMethod.GET)
    public ResponseEntity findAllCards() {
        Collection<CardDTO> dtos = cardService.findAllCards();
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @ApiOperation(value = "Deleting card by ID")
    @RequestMapping(value = "/clients/{clientId}accounts/{accountId}/cards/{cardId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable long clientId, @PathVariable long accountId,
                                     @PathVariable long cardId) {
        ResponseEntity responseEntity;
        try {
            clientService.findById(clientId);
            if (accountService.isContain(clientId, accountId)) {
                if (cardService.isContain(accountId, cardId)) {
                    cardService.deleteById(cardId);
                    responseEntity = ResponseEntity.status(HttpStatus.OK).build();
                } else {
                    responseEntity = getInternalServerErrorResponseEntity("Account do not contain this card");
                }
            } else {
                responseEntity = getInternalServerErrorResponseEntity("Client do not contain this account");
            }
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting card by ID")
    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}/cards/{cardId}",
            method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable long clientId, @PathVariable long accountId,
                                   @PathVariable long cardId) {
        ResponseEntity responseEntity;
        try {
            clientService.findById(clientId);
            if (accountService.isContain(clientId, accountId)) {
                if (cardService.isContain(accountId, cardId)) {
                    CardDTO dto = cardService.findById(cardId);
                    responseEntity = ResponseEntity.status(HttpStatus.OK).body(dto);
                } else {
                    responseEntity = getInternalServerErrorResponseEntity("Account do not contain this card");
                }
            } else {
                responseEntity = getInternalServerErrorResponseEntity("Client dot not contain this account");
            }
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    private ResponseEntity getNotFoundResponseEntity(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    private ResponseEntity getInternalServerErrorResponseEntity(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}
