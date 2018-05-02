package com.netcracker.komarov.controllers.controller;

import com.netcracker.komarov.controllers.exception.ServerException;
import com.netcracker.komarov.services.dto.entity.UnlockCardRequestDTO;
import com.netcracker.komarov.services.interfaces.UnlockCardRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class UnlockCardRequestController implements ExceptionController {
    private UnlockCardRequestService requestService;

    @Autowired
    public UnlockCardRequestController(UnlockCardRequestService requestService) {
        this.requestService = requestService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}/cards/{cardId}/request", method = RequestMethod.GET)
    public UnlockCardRequestDTO createRequest(@PathVariable long clientId, @PathVariable long accountId,
                                              @PathVariable long cardId) {
        UnlockCardRequestDTO dto = requestService.addCardRequest(cardId);
        if (dto == null) {
            throw new ServerException("Server can't create new request");
        }
        return dto;
    }
}
