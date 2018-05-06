package com.netcracker.komarov.controllers.controller;

import com.google.gson.Gson;
import com.netcracker.komarov.services.dto.entity.CardDTO;
import com.netcracker.komarov.services.interfaces.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("bank/v1")
public class CardController {
    private CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}/cards", method = RequestMethod.POST)
    public ResponseEntity create(@PathVariable long clientId, @PathVariable long accountId, @RequestBody CardDTO cardDTO) {
        CardDTO dto = cardService.createCard(cardDTO, accountId);
        Gson gson = new Gson();
        ResponseEntity responseEntity;
        if (dto == null) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(gson.toJson("Server error"));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.CREATED)
                    .body(gson.toJson(dto));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}/cards/{cardId}", method = RequestMethod.PATCH)
    public ResponseEntity lock(@PathVariable long clientId, @PathVariable long accountId, @PathVariable long cardId) {
        Gson gson = new Gson();
        CardDTO dto = cardService.lockCard(cardId);
        ResponseEntity responseEntity;
        if (dto == null) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(gson.toJson("Server error"));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(gson.toJson(dto));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/admins/requests/cards/{cardId}", method = RequestMethod.PATCH)
    public ResponseEntity unlock(@PathVariable long cardId) {
        Gson gson = new Gson();
        CardDTO dto = cardService.unlockCard(cardId);
        ResponseEntity responseEntity;
        if (dto == null) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(gson.toJson("Server error"));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(gson.toJson(dto));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/clients/{clientId}/cards/status", method = RequestMethod.GET)
    public ResponseEntity getByClientIdAndLock(@PathVariable long clientId, @RequestParam(name = "lock",
            required = false, defaultValue = "false") boolean lock) {
        Gson gson = new Gson();
        Collection<CardDTO> dtos = cardService.getCardsByClientIdAndLock(clientId, lock);
        ResponseEntity responseEntity;
        if (dtos == null) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server error");
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(gson.toJson(dtos));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/clients/{clientId}/accounts{accountId}/cards", method = RequestMethod.GET)
    public ResponseEntity getByAccountId(@PathVariable long clientId, @PathVariable long accountId) {
        Collection<CardDTO> dtos = cardService.getAllCardsByAccountId(accountId);
        Gson gson = new Gson();
        ResponseEntity responseEntity;
        if (dtos == null) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(gson.toJson("Server error"));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(gson.toJson(dtos));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/admins/cards", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        Collection<CardDTO> dtos = cardService.getAllCards();
        ResponseEntity responseEntity;
        Gson gson = new Gson();
        if (dtos == null) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(gson.toJson("Server error"));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(gson.toJson(dtos.isEmpty() ? "Empty list of cards" : dtos));
        }
        return responseEntity;
    }
}
