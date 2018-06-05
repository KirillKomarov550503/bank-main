package com.netcracker.komarov.controllers.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.komarov.services.dto.entity.CardDTO;
import com.netcracker.komarov.services.exception.LogicException;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.exception.ValidationException;
import com.netcracker.komarov.services.feign.RequestFeignClient;
import com.netcracker.komarov.services.interfaces.AccountService;
import com.netcracker.komarov.services.interfaces.CardService;
import com.netcracker.komarov.services.interfaces.PersonService;
import com.netcracker.komarov.services.validator.impl.CardValidator;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/bank/v1")
public class CardController {
    private CardService cardService;
    private PersonService personService;
    private AccountService accountService;
    private CardValidator cardValidator;
    private ObjectMapper objectMapper;
    private Environment environment;
    private RequestFeignClient requestFeignClient;

    @Autowired
    public CardController(CardService cardService, PersonService personService, AccountService accountService,
                          CardValidator cardValidator, ObjectMapper objectMapper, Environment environment,
                          RequestFeignClient requestFeignClient) {
        this.cardService = cardService;
        this.personService = personService;
        this.accountService = accountService;
        this.cardValidator = cardValidator;
        this.objectMapper = objectMapper;
        this.environment = environment;
        this.requestFeignClient = requestFeignClient;
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
                responseEntity = getErrorResponse(HttpStatus.FORBIDDEN, environment.getProperty("http.forbidden"));
            }
        } catch (NotFoundException e) {
            responseEntity = getErrorResponse(HttpStatus.FORBIDDEN, environment.getProperty("http.forbidden"));
        } catch (ValidationException | LogicException e) {
            responseEntity = getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Lock card by ID")
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
                    responseEntity = getErrorResponse(HttpStatus.FORBIDDEN, environment.getProperty("http.forbidden"));
                }
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

    @ApiOperation(value = "Unlock card by ID")
    @RequestMapping(value = "/admins/requests/cards/{cardId}", method = RequestMethod.PATCH)
    public ResponseEntity unlockCard(@PathVariable long cardId) {
        ResponseEntity responseEntity;
        try {
            CardDTO dto = cardService.unlockCard(cardId);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (NotFoundException e) {
            responseEntity = getErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (LogicException e) {
            responseEntity = getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Select all card by status")
    @RequestMapping(value = "/clients/{personId}/cards/status", method = RequestMethod.GET)
    public ResponseEntity findByClientIdAndLock(@PathVariable long personId, @RequestParam(name = "lockAccount",
            required = false, defaultValue = "false") boolean lock) {
        ResponseEntity responseEntity;
        try {
            Collection<CardDTO> dtos = cardService.findCardsByClientIdAndLock(personId, lock);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(dtos);
        } catch (NotFoundException e) {
            responseEntity = getErrorResponse(HttpStatus.FORBIDDEN, environment.getProperty("http.forbidden"));
        }
        return responseEntity;
    }

    @ApiOperation(value = "Select all card that belong to account ID")
    @RequestMapping(value = "/clients/{personId}/accounts/{accountId}/cards", method = RequestMethod.GET)
    public ResponseEntity findByAccountId(@PathVariable long personId, @PathVariable long accountId) {
        ResponseEntity responseEntity;
        try {
            personService.findById(personId);
            if (accountService.isContain(personId, accountId)) {
                Collection<CardDTO> dtos = cardService.findCardsByAccountId(accountId);
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(dtos);
            } else {
                responseEntity = getErrorResponse(HttpStatus.FORBIDDEN, environment.getProperty("http.forbidden"));
            }
        } catch (NotFoundException e) {
            responseEntity = getErrorResponse(HttpStatus.FORBIDDEN, environment.getProperty("http.forbidden"));
        }
        return responseEntity;
    }

    @ApiOperation(value = "Select all cards")
    @RequestMapping(value = "/admins/cards", method = RequestMethod.GET)
    public ResponseEntity findAllCards() {
        Collection<CardDTO> dtos = cardService.findAllCards();
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @ApiOperation(value = "Delete card by ID")
    @RequestMapping(value = "/clients/{personId}/accounts/{accountId}/cards/{cardId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable long personId, @PathVariable long accountId,
                                     @PathVariable long cardId) {
        ResponseEntity responseEntity;
        try {
            personService.findById(personId);
            if (accountService.isContain(personId, accountId)) {
                if (cardService.isContain(accountId, cardId)) {
                    Map<String, Long> entityMap = new HashMap<>();
                    entityMap.put("CARD", cardId);
                    requestFeignClient.deleteByEntityIdAndStatus(entityMap);
                    cardService.deleteById(cardId);
                    responseEntity = ResponseEntity.status(HttpStatus.OK).build();
                } else {
                    responseEntity = getErrorResponse(HttpStatus.FORBIDDEN, environment.getProperty("http.forbidden"));
                }
            } else {
                responseEntity = getErrorResponse(HttpStatus.FORBIDDEN, environment.getProperty("http.forbidden"));
            }
        } catch (NotFoundException e) {
            responseEntity = getErrorResponse(HttpStatus.FORBIDDEN, environment.getProperty("http.forbidden"));
        }
        return responseEntity;
    }

    @ApiOperation(value = "Select card by ID")
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
                    responseEntity = getErrorResponse(HttpStatus.FORBIDDEN, environment.getProperty("http.forbidden"));
                }
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
