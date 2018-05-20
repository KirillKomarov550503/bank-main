package com.netcracker.komarov.controllers.controller;

import com.netcracker.komarov.services.dto.entity.ClientDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.interfaces.ClientService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank/v1")
public class ClientController {
    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @ApiOperation(value = "Registration of news client")
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody PersonDTO personDTO) {
        ClientDTO dto = clientService.save(personDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @ApiOperation(value = "Updating information about client")
    @RequestMapping(value = "/clients/{clientId}", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody ClientDTO requestClientDTO, @PathVariable long clientId) {
        ResponseEntity responseEntity;
        try {
            requestClientDTO.setId(clientId);
            ClientDTO dto = clientService.update(requestClientDTO);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Deleting client by ID")
    @RequestMapping(value = "/clients/{clientId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable long clientId) {
        ResponseEntity responseEntity;
        try {
            clientService.deleteById(clientId);
            responseEntity = ResponseEntity.status(HttpStatus.OK).build();
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting client by ID")
    @RequestMapping(value = "/clients/{clientId}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable long clientId) {
        ResponseEntity responseEntity;
        try {
            ClientDTO dto = clientService.findById(clientId);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    private ResponseEntity getNotFoundResponseEntity(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}
