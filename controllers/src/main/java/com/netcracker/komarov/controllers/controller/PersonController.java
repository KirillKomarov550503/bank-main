package com.netcracker.komarov.controllers.controller;

import com.netcracker.komarov.dao.entity.Role;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.exception.LogicException;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.exception.ValidationException;
import com.netcracker.komarov.services.interfaces.PersonService;
import com.netcracker.komarov.services.validator.impl.PersonValidator;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/bank/v1")
public class PersonController {
    private PersonService personService;
    private PersonValidator personValidator;

    @Autowired
    public PersonController(PersonService personService, PersonValidator personValidator) {
        this.personService = personService;
        this.personValidator = personValidator;
    }

    @ApiOperation(value = "Registration of news client")
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity saveClient(@RequestBody PersonDTO personDTO) {
        ResponseEntity responseEntity;
        try {
            personDTO.setRole(Role.CLIENT);
            personValidator.validate(personDTO);
            PersonDTO dto = personService.save(personDTO);
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (LogicException e) {
            responseEntity = getInternalServerErrorResponseEntity(e.getMessage());
        } catch (ValidationException e) {
            responseEntity = getBadRequestResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Creation of new admin")
    @RequestMapping(value = "/admins", method = RequestMethod.POST)
    public ResponseEntity saveAdmin(@RequestBody PersonDTO personDTO) {
        ResponseEntity responseEntity;
        try {
            personDTO.setRole(Role.ADMIN);
            personValidator.validate(personDTO);
            PersonDTO dto = personService.save(personDTO);
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (LogicException e) {
            responseEntity = getInternalServerErrorResponseEntity(e.getMessage());
        } catch (ValidationException e) {
            responseEntity = getBadRequestResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Updating information about client")
    @RequestMapping(value = "/admins/{personId}", method = RequestMethod.PUT)
    public ResponseEntity updateAdmin(@RequestBody PersonDTO personDTO, @PathVariable long personId) {
        return getUpdateResponseEntity(personDTO, personId);
    }

    @ApiOperation(value = "Updating information about client")
    @RequestMapping(value = "/clients/{personId}", method = RequestMethod.PUT)
    public ResponseEntity updateClient(@RequestBody PersonDTO personDTO, @PathVariable long personId) {
        return getUpdateResponseEntity(personDTO, personId);
    }

    @ApiOperation(value = "Selecting all people")
    @RequestMapping(value = "/admins", method = RequestMethod.GET)
    public ResponseEntity findAllPeople() {
        Collection<PersonDTO> dtos = personService.findAllPeople();
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @ApiOperation(value = "Deleting client by ID")
    @RequestMapping(value = "/admins/{personId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteAdminById(@PathVariable long personId) {
        ResponseEntity responseEntity;
        try {
            personService.deleteById(personId);
            responseEntity = ResponseEntity.status(HttpStatus.OK).build();
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Deleting client by ID")
    @RequestMapping(value = "/clients/{personId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteClientById(@PathVariable long personId) {
        ResponseEntity responseEntity;
        try {
            personService.deleteById(personId);
            responseEntity = ResponseEntity.status(HttpStatus.OK).build();
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        }
        return responseEntity;
    }


    @ApiOperation(value = "Selecting person by ID")
    @RequestMapping(value = "/admins/{personId}", method = RequestMethod.GET)
    public ResponseEntity findAdminById(@PathVariable long personId) {
        ResponseEntity responseEntity;
        try {
            PersonDTO dto = personService.findById(personId);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (NotFoundException e) {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting person by ID")
    @RequestMapping(value = "/clients/{personId}", method = RequestMethod.GET)
    public ResponseEntity findClientById(@PathVariable long personId) {
        ResponseEntity responseEntity;
        try {
            PersonDTO dto = personService.findById(personId);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (NotFoundException e) {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return responseEntity;
    }

    private ResponseEntity getUpdateResponseEntity(@RequestBody PersonDTO personDTO, @PathVariable long personId) {
        ResponseEntity responseEntity;
        try {
            personValidator.validate(personDTO);
            personDTO.setId(personId);
            PersonDTO dto = personService.update(personDTO);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        } catch (ValidationException e) {
            responseEntity = getBadRequestResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    private ResponseEntity getNotFoundResponseEntity(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    private ResponseEntity getInternalServerErrorResponseEntity(String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }

    private ResponseEntity getBadRequestResponseEntity(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}
