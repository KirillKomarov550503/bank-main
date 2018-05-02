package com.netcracker.komarov.controllers.controller;

import com.netcracker.komarov.controllers.exception.ServerException;
import com.netcracker.komarov.services.dto.entity.ClientDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.interfaces.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class ClientController implements ExceptionController {
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
}
