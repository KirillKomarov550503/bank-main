package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Person;
import com.netcracker.komarov.dao.repository.PersonRepository;
import com.netcracker.komarov.services.dto.converter.PersonConverter;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.interfaces.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {
    private PersonRepository personRepository;
    private PersonConverter personConverter;
    private static final  Logger LOGGER = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, PersonConverter personConverter) {
        this.personRepository = personRepository;
        this.personConverter = personConverter;
    }

    private Collection<PersonDTO> convertCollection(Collection<Person> people) {
        return people.stream()
                .map(person -> personConverter.convertToDTO(person))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Collection<PersonDTO> findAllPeople() {
        LOGGER.info("Return all people");
        return convertCollection(personRepository.findAll());
    }

    @Transactional
    @Override
    public PersonDTO findById(long personId) throws NotFoundException {
        Optional<Person> optionalPerson = personRepository.findById(personId);
        Person person;
        if (optionalPerson.isPresent()) {
            person = optionalPerson.get();
            LOGGER.info("Return person with ID " + personId);
        } else {
            String error = "There is no such person with ID " + personId;
            LOGGER.error(error);
            throw new NotFoundException(error);
        }
        return personConverter.convertToDTO(person);
    }
}

