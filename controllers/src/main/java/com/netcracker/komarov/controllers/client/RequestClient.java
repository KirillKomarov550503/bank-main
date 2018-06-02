package com.netcracker.komarov.controllers.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.komarov.services.dto.Status;
import com.netcracker.komarov.services.dto.entity.RequestDTO;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.feign.RequestFeignClient;
import com.netcracker.komarov.services.interfaces.AccountService;
import com.netcracker.komarov.services.interfaces.CardService;
import com.netcracker.komarov.services.interfaces.PersonService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class RequestClient {
    private CardService cardService;
    private PersonService personService;
    private AccountService accountService;
    private RequestFeignClient requestFeignClient;
    private ObjectMapper objectMapper;

    @Autowired
    public RequestClient(CardService cardService, PersonService personService, AccountService accountService,
                         RequestFeignClient requestFeignClient, ObjectMapper objectMapper) {
        this.cardService = cardService;
        this.personService = personService;
        this.accountService = accountService;
        this.requestFeignClient = requestFeignClient;
        this.objectMapper = objectMapper;
    }

    public ResponseEntity sendRequestToUnlockAccount(long personId, long accountId) {
        ResponseEntity responseEntity;
        try {
            personService.findById(personId);
            if (accountService.isContain(personId, accountId)) {
                if (accountService.findById(accountId).isLocked()) {
                    responseEntity = requestFeignClient.save(new RequestDTO(accountId, Status.ACCOUNT));
                } else {
                    responseEntity = getResponseEntityOfInternalServerError("Account is unlock");
                }
            } else {
                responseEntity = getResponseEntityOfInternalServerError("You do not have access to this account");
            }
        } catch (NotFoundException e) {
            responseEntity = getResponseEntityOfNotFoundException(e.getMessage());
        } catch (FeignException e) {
            responseEntity = getResponseEntityOfFeignException(e);
        }
        return responseEntity;
    }

    public ResponseEntity sendRequestToUnlockCard(long personId, long accountId, long cardId) {
        ResponseEntity responseEntity;
        try {
            personService.findById(personId);
            if (accountService.isContain(personId, accountId)) {
                if (cardService.isContain(accountId, cardId)) {
                    if (cardService.findById(cardId).isLocked()) {
                        responseEntity = requestFeignClient.save(new RequestDTO(cardId, Status.CARD));
                    } else {
                        responseEntity = getResponseEntityOfInternalServerError("Card is unlock");
                    }
                } else {
                    responseEntity = getResponseEntityOfInternalServerError("You do not have access to this card");
                }
            } else {
                responseEntity = getResponseEntityOfInternalServerError("You do not have access to this account");
            }
        } catch (NotFoundException e) {
            responseEntity = getResponseEntityOfNotFoundException(e.getMessage());
        } catch (FeignException e) {
            responseEntity = getResponseEntityOfFeignException(e);
        }
        return responseEntity;
    }

    public ResponseEntity findAllRequests() {
        return requestFeignClient.findAllRequests();
    }

    public ResponseEntity deleteById(long requestId) {
        ResponseEntity responseEntity;
        try {
            responseEntity = requestFeignClient.deleteById(requestId);
        } catch (FeignException e) {
            responseEntity = getResponseEntityOfFeignException(e);
        }
        return responseEntity;
    }

    public ResponseEntity findById(long requestId) {
        ResponseEntity responseEntity;
        try {
            responseEntity = requestFeignClient.findById(requestId);
        } catch (FeignException e) {
            responseEntity = getResponseEntityOfFeignException(e);
        }
        return responseEntity;
    }

    private ResponseEntity getResponseEntityOfFeignException(FeignException e) {
        String message = Stream.of(e.getMessage().split("\n"))
                .skip(1)
                .reduce((s1, s2) -> s1 + s2).orElse(null);
        return ResponseEntity.status(e.status()).body(objectMapper.valueToTree(message));
    }

    private ResponseEntity getResponseEntityOfNotFoundException(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(objectMapper.valueToTree(message));
    }

    private ResponseEntity getResponseEntityOfInternalServerError(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(objectMapper.valueToTree(message));
    }
}
