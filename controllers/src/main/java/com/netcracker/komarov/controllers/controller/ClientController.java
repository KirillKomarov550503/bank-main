package com.netcracker.komarov.controllers.controller;

import com.google.gson.Gson;
import com.netcracker.komarov.services.dto.entity.ClientDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.interfaces.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bank/v1")
public class ClientController {
    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody PersonDTO personDTO) {
        Gson gson = new Gson();
        ClientDTO dto = clientService.save(personDTO);
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

    @RequestMapping(value = "/clients/{clientId}", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody ClientDTO clientDTO, @PathVariable long clientId) {
        Gson gson = new Gson();
        ClientDTO dto = clientService.update(clientDTO, clientId);
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
}
