package com.netcracker.komarov.controllers.controller;

import com.netcracker.komarov.controllers.exception.ServerException;
import com.netcracker.komarov.services.dto.entity.CardDTO;
import com.netcracker.komarov.services.interfaces.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("api/v1")
public class CardController implements ExceptionController {
    private CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}/cards", method = RequestMethod.POST)
    public CardDTO create(@PathVariable long clientId, @PathVariable long accountId, @RequestBody CardDTO cardDTO) {
        CardDTO dto = cardService.createCard(cardDTO, accountId);
        if (dto == null) {
            throw new ServerException("Server can't create new card");
        }
        return dto;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}/cards/{cardId}/blocking", method = RequestMethod.GET)
    public CardDTO lock(@PathVariable long clientId, @PathVariable long accountId, @PathVariable long cardId) {
        CardDTO dto = cardService.lockCard(cardId);
        if (dto == null) {
            throw new NullPointerException("card");
        } else if (!dto.isLocked()) {
            throw new ServerException("Failed to lock card");
        }
        return dto;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/admins/{clientId}/requests/cards/{cardId}/unblocking", method = RequestMethod.GET)
    public CardDTO unlock(@PathVariable long clientId, @PathVariable long cardId) {
        CardDTO dto = cardService.lockCard(cardId);
        if (dto == null) {
            throw new NullPointerException("card");
        } else if (dto.isLocked()) {
            throw new ServerException("Failed to lock card");
        }
        return dto;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/clients/{clientId}/cards/status", method = RequestMethod.GET)
    public Collection<CardDTO> getByClientIdAndLock(@PathVariable long clientId, @RequestParam(name = "lock",
            required = false, defaultValue = "false") boolean lock) {
        Collection<CardDTO> dtos = cardService.getCardsByClientIdAndLock(clientId, lock);
        if (dtos == null) {
            throw new NullPointerException("card");
        }
        return dtos;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/clients/{clientId}/accounts{accountId}/cards", method = RequestMethod.GET)
    public Collection<CardDTO> getByAccountId(@PathVariable long clientId, @PathVariable long accountId) {
        Collection<CardDTO> dtos = cardService.getAllCardsByAccountId(accountId);
        if (dtos == null) {
            throw new NullPointerException("account");
        }
        return dtos;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/admins/{adminId}/cards", method = RequestMethod.GET)
    public Collection<CardDTO> getAll(@PathVariable long adminId) {
        return cardService.getAllCards();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/admins/{adminId}/requests/cards", method = RequestMethod.GET)
    public Collection<CardDTO> getAllUnlockRequests(@PathVariable long adminId) {
        return cardService.getAllCardRequest();
    }
}
