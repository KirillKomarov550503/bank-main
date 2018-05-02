package com.netcracker.komarov.controllers.controller;

import com.netcracker.komarov.controllers.exception.ServerException;
import com.netcracker.komarov.services.dto.entity.UnlockAccountRequestDTO;
import com.netcracker.komarov.services.interfaces.UnlockAccountRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class UnlockAccountRequestController {
    private UnlockAccountRequestService requestService;

    @Autowired
    public UnlockAccountRequestController(UnlockAccountRequestService requestService) {
        this.requestService = requestService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/clients{clientId}/accounts/{accountId}/requests", method = RequestMethod.POST)
    public UnlockAccountRequestDTO addRequest(@PathVariable long clientId, @PathVariable long accountId) {
        UnlockAccountRequestDTO dto = requestService.addAccountRequest(accountId);
        if (dto == null) {
            throw new ServerException("Server can't create new request");
        }
        return dto;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = ServerException.class)
    public String handleServerException(ServerException ex) {
        return ex.getMessage();
    }
}
