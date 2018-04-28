package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Person;
import com.netcracker.komarov.dao.factory.RepositoryFactory;
import com.netcracker.komarov.dao.repository.PersonRepository;
import com.netcracker.komarov.services.interfaces.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class PersonServiceImpl implements PersonService {
    private PersonRepository personRepository;
    private Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Autowired
    public PersonServiceImpl(RepositoryFactory repositoryFactory) {
        this.personRepository = repositoryFactory.getPersonRepository();
    }

    @Transactional
    @Override
    public Collection<Person> getAllPeople() {
        logger.info("Return all people");
        return personRepository.findAll();
    }
}

