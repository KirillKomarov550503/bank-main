package com.netcracker.komarov.controllers.controller;

import com.netcracker.komarov.controllers.client.RequestClient;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank/v1")
public class RequestController {
    private RequestClient requestClient;

    @Autowired
    public RequestController(RequestClient requestClient) {
        this.requestClient = requestClient;
    }

    @ApiOperation(value = "Send request to unlock account")
    @RequestMapping(value = "/clients/{personId}/accounts/{accountId}/requests", method = RequestMethod.PATCH)
    public ResponseEntity sendRequestToUnlockAccount(@PathVariable long personId,
                                                     @PathVariable long accountId) {
        return requestClient.sendRequestToUnlockAccount(personId, accountId);
    }

    @ApiOperation(value = "Send request to unlock card")
    @RequestMapping(value = "/clients/{personId}/accounts/{accountId}/cards/{cardId}/requests",
            method = RequestMethod.PATCH)
    public ResponseEntity sendRequestToUnlockCard(@PathVariable long personId,
                                                  @PathVariable long accountId,
                                                  @PathVariable long cardId) {
        return requestClient.sendRequestToUnlockCard(personId, accountId, cardId);
    }

    @ApiOperation(value = "Select all requests")
    @RequestMapping(value = "/admins/requests", method = RequestMethod.GET)
    public ResponseEntity findAllRequests() {
        return requestClient.findAllRequests();
    }

    @ApiOperation(value = "Unlock something by request ID")
    @RequestMapping(value = "/admins/requests/{requestId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable long requestId) {
        return requestClient.deleteById(requestId);
    }

    @ApiOperation(value = "Select request by ID")
    @RequestMapping(value = "/admins/requests/{requestId}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable long requestId) {
        return requestClient.findById(requestId);
    }
}