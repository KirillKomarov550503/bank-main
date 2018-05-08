package com.netcracker.komarov.controllers.controller;

import com.google.gson.Gson;
import com.netcracker.komarov.services.dto.entity.ClientDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.interfaces.ClientService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bank/v1")
public class ClientController {
    private ClientService clientService;
    private Gson gson;

    @Autowired
    public ClientController(ClientService clientService, Gson gson) {
        this.clientService = clientService;
        this.gson = gson;
    }

    @ApiOperation(value = "Registration of news client")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody PersonDTO personDTO) {
        ClientDTO dto = clientService.save(personDTO);
        ResponseEntity responseEntity;
        if (dto == null) {
            responseEntity = internalServerError("Server error");
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.CREATED)
                    .body(gson.toJson(dto));
        }
        return responseEntity;
    }

    @ApiOperation(value = "Updating information about client")
    @RequestMapping(value = "/clients/{clientId}", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody ClientDTO requestClientDTO, @PathVariable long clientId) {
        ResponseEntity responseEntity;
        ClientDTO clientDTO = clientService.findById(clientId);
        if (clientDTO == null) {
            responseEntity = notFound("No such client in database");
        } else {
            requestClientDTO.setId(clientId);
            ClientDTO dto = clientService.update(requestClientDTO);
            if (dto == null) {
                responseEntity = internalServerError("Server error");
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(gson.toJson(dto));
            }
        }
        return responseEntity;
    }

    @ApiOperation(value = "Deleting client by ID")
    @RequestMapping(value = "/clients/{clientId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable long clientId) {
        ClientDTO clientDTO = clientService.findById(clientId);
        ResponseEntity responseEntity;
        if (clientDTO == null) {
            responseEntity = notFound("No such client");
        } else {
            clientService.deleteById(clientId);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(gson.toJson("Client was deleted"));
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
