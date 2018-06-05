package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Account;
import com.netcracker.komarov.dao.entity.Card;
import com.netcracker.komarov.dao.entity.Person;
import com.netcracker.komarov.dao.repository.PersonRepository;
import com.netcracker.komarov.services.dto.converter.PersonConverter;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.exception.LogicException;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.feign.RequestFeignClient;
import com.netcracker.komarov.services.interfaces.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:application-error.properties")
public class PersonServiceImpl implements PersonService {
    private PersonRepository personRepository;
    private PersonConverter personConverter;
    private PasswordEncoder passwordEncoder;
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonServiceImpl.class);
    private Environment environment;
    private RequestFeignClient requestFeignClient;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, PersonConverter personConverter,
                             PasswordEncoder passwordEncoder, Environment environment,
                             RequestFeignClient requestFeignClient) {
        this.personRepository = personRepository;
        this.personConverter = personConverter;
        this.passwordEncoder = passwordEncoder;
        this.environment = environment;
        this.requestFeignClient = requestFeignClient;
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
            String error = environment.getProperty("error.person.search") + personId;
            LOGGER.error(error);
            throw new NotFoundException(error);
        }
        return personConverter.convertToDTO(person);
    }

    @Transactional
    @Override
    public PersonDTO save(PersonDTO personDTO) {
        Person person = personConverter.convertToEntity(personDTO);
        Person temp = personRepository.findPersonByUsername(person.getUsername());
        Person newPerson;
        if (temp == null) {
            String password = person.getPassword();
            person.setPassword(passwordEncoder.encode(password));
            newPerson = personRepository.save(person);
            LOGGER.info("Add to system new person with ID " + newPerson.getId());
        } else {
            String error = "This username is already exist";
            LOGGER.error(error);
            throw new LogicException(error);
        }
        return personConverter.convertToDTO(newPerson);
    }

    @Transactional
    @Override
    public PersonDTO update(PersonDTO personDTO) {
        Person newPerson = personConverter.convertToEntity(personDTO);
        Optional<Person> optionalPerson = personRepository.findById(personDTO.getId());
        Person person;
        if (optionalPerson.isPresent()) {
            Person oldPerson = optionalPerson.get();
            newPerson.setId(oldPerson.getId());
            newPerson.setRole(oldPerson.getRole());
            String password = newPerson.getPassword();
            newPerson.setPassword(passwordEncoder.encode(password));
            person = personRepository.saveAndFlush(oldPerson);
            LOGGER.info("Information about person with ID " + personDTO.getId() + " was updated");
        } else {
            String error = environment.getProperty("error.person.search") + personDTO.getId();
            LOGGER.error(error);
            throw new NotFoundException(error);
        }
        return personConverter.convertToDTO(person);
    }

    @Transactional
    @Override
    public void deleteById(long personId) {
        Optional<Person> optionalPerson = personRepository.findById(personId);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            Set<Account> accounts = person.getAccounts();
            for (Account account : accounts) {
                for (Card card : account.getCards()) {
                    requestFeignClient.deleteById(card.getId(), "CARD");
                }
                requestFeignClient.deleteById(account.getId(), "ACCOUNT");
            }
            personRepository.deletePersonById(personId);
            LOGGER.info("Person with ID " + personId + " was deleted");
        } else {
            String error = environment.getProperty("error.person.search") + personId;
            LOGGER.error(error);
            throw new NotFoundException(error);
        }
    }
}

