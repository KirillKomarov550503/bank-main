package com.netcracker.komarov.controllers.controller;

import com.google.gson.Gson;
import com.netcracker.komarov.services.dto.entity.AccountDTO;
import com.netcracker.komarov.services.dto.entity.CardDTO;
import com.netcracker.komarov.services.dto.entity.ClientDTO;
import com.netcracker.komarov.services.interfaces.AccountService;
import com.netcracker.komarov.services.interfaces.CardService;
import com.netcracker.komarov.services.interfaces.ClientService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.Collection;

@RestController
@RequestMapping("bank/v1")
public class CardController {
    private CardService cardService;
    private ClientService clientService;
    private AccountService accountService;
    private Gson gson;

    @Autowired
    public CardController(CardService cardService, ClientService clientService,
                          AccountService accountService, Gson gson) {
        this.cardService = cardService;
        this.clientService = clientService;
        this.accountService = accountService;
        this.gson = gson;
    }

    @ApiOperation(value = "Creation of new card")
    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}/cards", method = RequestMethod.POST)
    public ResponseEntity create(@PathVariable long clientId, @PathVariable long accountId,
                                 @RequestBody CardDTO requestCardDTO) {
        ResponseEntity responseEntity;
        ClientDTO clientDTO = clientService.findById(clientId);
        if (clientDTO == null) {
            responseEntity = notFound("No such client in database");
        } else {
            AccountDTO accountDTO = accountService.findById(accountId);
            if (accountDTO == null) {
                responseEntity = notFound("No such account in database");
            } else {
                CardDTO dto = cardService.createCard(requestCardDTO);
                requestCardDTO.setAccountId(accountId);
                if (dto == null) {
                    responseEntity = internalServerError("Server error");
                } else {
                    responseEntity = ResponseEntity.status(HttpStatus.CREATED)
                            .body(gson.toJson(dto));
                }
            }
        }

        return responseEntity;
    }

    @ApiOperation(value = "Locking of card by ID")
    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}/cards/{cardId}", method = RequestMethod.PATCH)
    public ResponseEntity lock(@PathVariable long clientId, @PathVariable long accountId,
                               @PathVariable long cardId) {
        ResponseEntity responseEntity;
        ClientDTO clientDTO = clientService.findById(clientId);
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
                    CardDTO dto = cardService.lockCard(cardId);
                    if (dto == null) {
                        responseEntity = internalServerError("Server error");
                    } else {
                        responseEntity = ResponseEntity.status(HttpStatus.OK).body(gson.toJson(dto));
                    }
                }
            }
        }

        return responseEntity;
    }

    @ApiOperation(value = "Unlocking card by ID")
    @RequestMapping(value = "/admins/requests/cards/{cardId}", method = RequestMethod.PATCH)
    public ResponseEntity unlock(@PathVariable long cardId) {
        ResponseEntity responseEntity;
        CardDTO cardDTO = cardService.findById(cardId);
        if (cardDTO == null) {
            responseEntity = notFound("No such client in database");
        } else {
            CardDTO dto = cardService.unlockCard(cardId);
            if (dto == null) {
                responseEntity = internalServerError("Server error");
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.OK)
                        .body(gson.toJson(dto));
            }
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting all card by status")
    @RequestMapping(value = "/clients/{clientId}/cards/status", method = RequestMethod.GET)
    public ResponseEntity getByClientIdAndLock(@PathVariable long clientId, @RequestParam(name = "lock",
            required = false, defaultValue = "false") boolean lock) {
        ResponseEntity responseEntity;
        ClientDTO clientDTO = clientService.findById(clientId);
        if (clientDTO == null) {
            responseEntity = notFound("No such client in database");
        } else {
            Collection<CardDTO> dtos = cardService.getCardsByClientIdAndLock(clientId, lock);
            if (dtos == null) {
                responseEntity = internalServerError("Server error");
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.OK)
                        .body(gson.toJson(dtos));
            }
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting all card that belong to account ID")
    @RequestMapping(value = "/clients/{clientId}/accounts{accountId}/cards", method = RequestMethod.GET)
    public ResponseEntity getByAccountId(@PathVariable long clientId, @PathVariable long accountId) {
        ResponseEntity responseEntity;
        ClientDTO clientDTO = clientService.findById(clientId);
        if (clientDTO == null) {
            responseEntity = notFound("No such client in database");
        } else {
            AccountDTO accountDTO = accountService.findById(accountId);
            if (accountDTO == null) {
                responseEntity = notFound("No such account in database");
            } else {
                Collection<CardDTO> dtos = cardService.getAllCardsByAccountId(accountId);
                if (dtos == null) {
                    responseEntity = internalServerError("Server error");
                } else {
                    responseEntity = ResponseEntity.status(HttpStatus.OK)
                            .body(gson.toJson(dtos));
                }
            }
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting all card")
    @RequestMapping(value = "/admins/cards", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        Collection<CardDTO> dtos = cardService.getAllCards();
        ResponseEntity responseEntity;
        if (dtos == null) {
            responseEntity = internalServerError("Server error");
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(gson.toJson(dtos.isEmpty() ? "Empty list of cards" : dtos));
        }
        return responseEntity;
    }

    @ApiOperation(value = "Deleting card by ID")
    @RequestMapping(value = "/cards/{cardId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable long cardId) {
        ResponseEntity responseEntity;
        CardDTO cardDTO = cardService.findById(cardId);
        if (cardDTO == null) {
            responseEntity = notFound("No such card in database");
        } else {
            cardService.deleteById(cardId);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(gson.toJson("Card was deleted"));
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting card by ID")
    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}/cards/{cardId}",
            method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable long clientId, @PathVariable long accountId,
                                   @PathVariable long cardId) {
        ResponseEntity responseEntity;
        ClientDTO clientDTO = clientService.findById(clientId);
        if (clientDTO == null) {
            responseEntity = notFound("No such client in database");
        } else {
            AccountDTO accountDTO = accountService.findById(accountId);
            if (accountDTO == null) {
                responseEntity = notFound("No such account in database");
            } else {
                CardDTO dto = cardService.findById(cardId);
                if (dto == null) {
                    responseEntity = notFound("No such card in database");
                } else {
                    responseEntity = ResponseEntity.status(HttpStatus.OK)
                            .body(gson.toJson(dto));
                }
            }
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
