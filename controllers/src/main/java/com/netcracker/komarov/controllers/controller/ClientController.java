package com.netcracker.komarov.controllers.controller;

import com.netcracker.komarov.controllers.exception.ServerException;
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

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public ClientDTO registration(@RequestBody PersonDTO personDTO) {
        ClientDTO dto = clientService.registration(personDTO);
        if (dto == null) {
            throw new ServerException("Server can't create new client");
        }
        return dto;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/admins/{adminId}/clients", method = RequestMethod.GET)
    public Collection<ClientDTO> getAll(@PathVariable long adminId) {
        return clientService.getAllClients();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = ServerException.class)
    public String handleServerException(ServerException ex) {
        return ex.getMessage();
    }
}
