package com.netcracker.komarov.controllers.controller;

import com.netcracker.komarov.services.dto.entity.ClientDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.interfaces.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("api/v1")
public class ClientController {
    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity registration(@RequestBody PersonDTO personDTO) {
        ClientDTO dto = clientService.registration(personDTO);
        ResponseEntity responseEntity;
        if (dto == null) {
            responseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            responseEntity = new ResponseEntity(dto, HttpStatus.CREATED);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/admins/{adminId}/clients", method = RequestMethod.GET)
    public Collection<ClientDTO> getAll(@PathVariable long adminId) {
        return clientService.getAllClients();
    }
}
