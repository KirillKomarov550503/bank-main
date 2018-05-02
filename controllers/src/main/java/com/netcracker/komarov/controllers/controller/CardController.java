package com.netcracker.komarov.controllers.controller;

import com.netcracker.komarov.services.dto.entity.CardDTO;
import com.netcracker.komarov.services.interfaces.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("api/v1")
public class CardController {
    private CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}/cards", method = RequestMethod.POST)
    public ResponseEntity create(@PathVariable long clientId, @PathVariable long accountId, @RequestBody CardDTO cardDTO) {
        CardDTO dto = cardService.createCard(cardDTO, accountId);
        ResponseEntity responseEntity;
        if (dto == null) {
            responseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            responseEntity = new ResponseEntity(dto, HttpStatus.CREATED);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}/cards/{cardId}/blocking", method = RequestMethod.GET)
    public ResponseEntity lock(@PathVariable long clientId, @PathVariable long accountId, @PathVariable long cardId) {
        CardDTO dto = cardService.lockCard(cardId);
        ResponseEntity responseEntity;
        if (dto == null) {
            responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        } else if (dto.isLocked()) {
            responseEntity = new ResponseEntity(dto, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/admins/{clientId}/requests/cards/{cardId}/unblocking", method = RequestMethod.GET)
    public ResponseEntity unlock(@PathVariable long clientId, @PathVariable long cardId) {
        CardDTO dto = cardService.lockCard(cardId);
        ResponseEntity responseEntity;
        if (dto == null) {
            responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        } else if (dto.isLocked()) {
            responseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            responseEntity = new ResponseEntity(dto, HttpStatus.OK);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/clients/{clientId}/cards/status", method = RequestMethod.GET)
    public ResponseEntity getByClientIdAndLock(@PathVariable long clientId, @RequestParam(name = "lock",
            required = false, defaultValue = "false") boolean lock) {
        Collection<CardDTO> dtos = cardService.getCardsByClientIdAndLock(clientId, lock);
        ResponseEntity responseEntity;
        if (dtos == null) {
            responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            responseEntity = new ResponseEntity(dtos, HttpStatus.OK);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/clients/{clientId}/accounts{accountId}/cards", method = RequestMethod.GET)
    public ResponseEntity getByAccountId(@PathVariable long clientId, @PathVariable long accountId) {
        Collection<CardDTO> dtos = cardService.getAllCardsByAccountId(accountId);
        ResponseEntity responseEntity;
        if (dtos == null) {
            responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            responseEntity = new ResponseEntity(dtos, HttpStatus.OK);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/admins/{adminId}/cards", method = RequestMethod.GET)
    public Collection<CardDTO> getAll(@PathVariable long adminId) {
        return cardService.getAllCards();
    }

    @RequestMapping(value = "/admins/{adminId}/requests/cards")
    public Collection<CardDTO> getAllUnlockRequests(@PathVariable long adminId) {
        return cardService.getAllUnlockCardRequest();
    }
}
