package com.netcracker.komarov.controllers.controller;

import com.google.gson.Gson;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.interfaces.PersonService;
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
@RequestMapping("/bank/v1")
public class PersonController {
    private PersonService personService;
    private Gson gson;

    @Autowired
    public PersonController(PersonService personService, Gson gson) {
        this.personService = personService;
        this.gson = gson;
    }

    @ApiOperation(value = "Selecting all people")
    @RequestMapping(value = "/admins/people", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        Collection<PersonDTO> dtos = personService.getAllPeople();
        Gson gson = new Gson();
        ResponseEntity responseEntity;
        if (dtos == null) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(gson.toJson("Server error"));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(gson.toJson(dtos.isEmpty() ? "Empty list of people" : dtos));
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting person by ID")
    @RequestMapping(value = "/admins/people/{personId}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable long personId) {
        ResponseEntity responseEntity;
        try {
            PersonDTO dto = personService.findById(personId);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(gson.toJson(dto));
        } catch (NotFoundException e) {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(gson.toJson(e.getMessage()));
        }
        return responseEntity;
    }
}
