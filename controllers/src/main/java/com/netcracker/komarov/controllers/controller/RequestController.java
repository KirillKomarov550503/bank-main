package com.netcracker.komarov.controllers.controller;

import com.google.gson.Gson;
import com.netcracker.komarov.services.dto.RequestStatus;
import com.netcracker.komarov.services.dto.entity.RequestDTO;
import com.netcracker.komarov.services.interfaces.RequestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("bank/v1")
public class RequestController {
    private RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @ApiOperation(value = "Sending request to unlock account")
    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}/requests", method = RequestMethod.PATCH)
    public ResponseEntity sendRequestToUnlockAccount(@PathVariable long clientId,
                                                     @PathVariable long accountId) {
        Gson gson = new Gson();
        ResponseEntity responseEntity;
        RequestDTO dto = requestService.saveRequest(accountId, RequestStatus.ACCOUNT);
        if (dto == null) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(gson.toJson("Server error"));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.CREATED)
                    .body(gson.toJson(dto));
        }
        return responseEntity;
    }

    @ApiOperation(value = "Sending request to unlock card")
    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}/cards/{cardId}/requests", method = RequestMethod.PATCH)
    public ResponseEntity sendRequestToUnlockCard(@PathVariable long clientId,
                                                  @PathVariable long accountId,
                                                  @PathVariable long cardId) {
        Gson gson = new Gson();
        ResponseEntity responseEntity;
        RequestDTO dto = requestService.saveRequest(cardId, RequestStatus.CARD);
        if (dto == null) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(gson.toJson("Server error"));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.CREATED)
                    .body(gson.toJson(dto));
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting all unlock requests")
    @RequestMapping(value = "/admins/requests/accounts", method = RequestMethod.GET)
    public ResponseEntity getAllRequests() {
        Gson gson = new Gson();
        Collection<RequestDTO> dtos = requestService.findAllRequests();
        ResponseEntity responseEntity;
        if (dtos == null) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(gson.toJson("Server error"));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(gson.toJson(dtos.isEmpty() ? "Empty list of requests" : dtos));
        }
        return responseEntity;
    }

    @ApiOperation(value = "Deleting request by ID")
    @RequestMapping(value = "/admins/requests/{requestId}/del", method = RequestMethod.DELETE)
    public void deleteRequest(@PathVariable long requestId) {
        requestService.delete(requestId);
    }
}
