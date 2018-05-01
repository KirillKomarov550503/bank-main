package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Person;
import com.netcracker.komarov.dao.factory.RepositoryFactory;
import com.netcracker.komarov.dao.repository.PersonRepository;
import com.netcracker.komarov.services.dto.converter.PersonConverter;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.interfaces.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {
    private PersonRepository personRepository;
    private PersonConverter personConverter;
    private Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Autowired
    public PersonServiceImpl(RepositoryFactory repositoryFactory, PersonConverter personConverter) {
        this.personRepository = repositoryFactory.getPersonRepository();
        this.personConverter = personConverter;
    }

    private Collection<PersonDTO> convertCollection(Collection<Person> people) {
        return people.stream()
                .map(person -> personConverter.convertToDTO(person))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Collection<PersonDTO> getAllPeople() {
        logger.info("Return all people");
        return convertCollection(personRepository.findAll());
    }
}

