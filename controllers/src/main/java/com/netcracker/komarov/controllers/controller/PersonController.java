package com.netcracker.komarov.controllers.controller;

import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.interfaces.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("api/v1")
public class PersonController {
    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/admins/{adminId}/people", method = RequestMethod.GET)
    public Collection<PersonDTO> getAll(@PathVariable long adminId) {
        return personService.getAllPeople();
    }
}
