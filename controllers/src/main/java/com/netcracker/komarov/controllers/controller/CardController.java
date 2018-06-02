package com.netcracker.komarov.controllers.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.komarov.services.dto.entity.CardDTO;
import com.netcracker.komarov.services.exception.LogicException;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.exception.ValidationException;
import com.netcracker.komarov.services.interfaces.AccountService;
import com.netcracker.komarov.services.interfaces.CardService;
import com.netcracker.komarov.services.interfaces.PersonService;
import com.netcracker.komarov.services.validator.impl.CardValidator;
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
    private PersonService personService;
    private AccountService accountService;
    private CardValidator cardValidator;
    private ObjectMapper objectMapper;

    @Autowired
    public CardController(CardService cardService, PersonService personService, AccountService accountService,
                          CardValidator cardValidator, ObjectMapper objectMapper) {
        this.cardService = cardService;
        this.personService = personService;
        this.accountService = accountService;
        this.cardValidator = cardValidator;
        this.objectMapper = objectMapper;
    }

    @ApiOperation(value = "Creation of new card")
    @RequestMapping(value = "/clients/{personId}/accounts/{accountId}/cards", method = RequestMethod.POST)
    public ResponseEntity save(@PathVariable long personId, @PathVariable long accountId,
                               @RequestBody CardDTO cardDTO) {
        ResponseEntity responseEntity;
        try {
            personService.findById(personId);
            if (accountService.isContain(personId, accountId)) {
                cardValidator.validate(cardDTO);
                cardDTO.setAccountId(accountId);
                CardDTO dto = cardService.save(cardDTO);
                responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(dto);
            } else {
                responseEntity = getInternalServerErrorResponseEntity("Client do not contain this account");
            }
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        } catch (ValidationException e) {
            responseEntity = getBadRequestResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Locking of card by ID")
    @RequestMapping(value = "/clients/{personId}/accounts/{accountId}/cards/{cardId}", method = RequestMethod.PATCH)
    public ResponseEntity lockCard(@PathVariable long personId, @PathVariable long accountId,
                                   @PathVariable long cardId) {
        ResponseEntity responseEntity;
        try {
            personService.findById(personId);
            if (accountService.isContain(personId, accountId)) {
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
    @RequestMapping(value = "/clients/{personId}/cards/status", method = RequestMethod.GET)
    public ResponseEntity findByClientIdAndLock(@PathVariable long personId, @RequestParam(name = "lockAccount",
            required = false, defaultValue = "false") boolean lock) {
        ResponseEntity responseEntity;
        try {
            Collection<CardDTO> dtos = cardService.findCardsByClientIdAndLock(personId, lock);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(dtos);
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting all card that belong to account ID")
    @RequestMapping(value = "/clients/{personId}/accounts/{accountId}/cards", method = RequestMethod.GET)
    public ResponseEntity findByAccountId(@PathVariable long personId, @PathVariable long accountId) {
        ResponseEntity responseEntity;
        try {
            personService.findById(personId);
            if (accountService.isContain(personId, accountId)) {
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
    @RequestMapping(value = "/clients/{personId}/accounts/{accountId}/cards/{cardId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable long personId, @PathVariable long accountId,
                                     @PathVariable long cardId) {
        ResponseEntity responseEntity;
        try {
            personService.findById(personId);
            if (accountService.isContain(personId, accountId)) {
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
    @RequestMapping(value = "/clients/{personId}/accounts/{accountId}/cards/{cardId}",
            method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable long personId, @PathVariable long accountId,
                                   @PathVariable long cardId) {
        ResponseEntity responseEntity;
        try {
            personService.findById(personId);
            if (accountService.isContain(personId, accountId)) {
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
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(objectMapper.valueToTree(message));
    }

    private ResponseEntity getInternalServerErrorResponseEntity(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(objectMapper.valueToTree(message));
    }

    private ResponseEntity getBadRequestResponseEntity(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(objectMapper.valueToTree(message));
    }
}
