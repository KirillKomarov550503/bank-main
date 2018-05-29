package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Client;
import com.netcracker.komarov.dao.entity.Person;
import com.netcracker.komarov.dao.entity.Role;
import com.netcracker.komarov.dao.repository.ClientRepository;
import com.netcracker.komarov.dao.repository.PersonRepository;
import com.netcracker.komarov.services.dto.converter.ClientConverter;
import com.netcracker.komarov.services.dto.converter.PersonConverter;
import com.netcracker.komarov.services.dto.entity.ClientDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.exception.LogicException;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.interfaces.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;
    private ClientConverter clientConverter;
    private PersonConverter personConverter;
    private PersonRepository personRepository;
    private PasswordEncoder passwordEncoder;
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, ClientConverter clientConverter,
                             PersonConverter personConverter, PersonRepository personRepository,
                             PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.clientConverter = clientConverter;
        this.personConverter = personConverter;
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private Collection<ClientDTO> convertCollection(Collection<Client> clients) {
        return clients.stream()
                .map(client -> clientConverter.convertToDTO(client))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ClientDTO save(PersonDTO personDTO) throws LogicException {
        Person person = personConverter.convertToEntity(personDTO);
        person.setRole(Role.CLIENT);
        Person temp = personRepository.findPersonByUsername(person.getUsername());
        Client clientRes;
        if (temp == null) {
            String password = person.getPassword();
            person.setPassword(passwordEncoder.encode(password));
            Client client = new Client();
            client.setPerson(person);
            person.setClient(client);
            clientRes = clientRepository.save(client);
            LOGGER.info("Registration of new client with ID " + personDTO.getId());
        } else {
            String error = "This username is already exist";
            LOGGER.error(error);
            throw new LogicException(error);
        }
        return clientConverter.convertToDTO(clientRes);
    }

    @Transactional
    @Override
    public Collection<ClientDTO> findAllClients() {
        LOGGER.info("Return all clients");
        return convertCollection(clientRepository.findAll());
    }

    @Transactional
    @Override
    public ClientDTO update(ClientDTO clientDTO) throws NotFoundException {
        Client newClient = clientConverter.convertToEntity(clientDTO);
        Optional<Client> optionalClient = clientRepository.findById(clientDTO.getId());
        Client resClient;
        if (optionalClient.isPresent()) {
            Client oldClient = optionalClient.get();
            Person newPerson = newClient.getPerson();
            String password = newPerson.getPassword();
            newPerson.setPassword(passwordEncoder.encode(password));
            Person oldPerson = oldClient.getPerson();
            newPerson.setId(oldPerson.getId());
            oldClient.setPerson(newPerson);
            resClient = clientRepository.saveAndFlush(oldClient);
            LOGGER.info("Information about client with ID " + clientDTO.getId() + " was updated");
        } else {
            String error = "There is no such client in database with ID " + clientDTO.getId();
            LOGGER.error(error);
            throw new NotFoundException(error);
        }
        return clientConverter.convertToDTO(resClient);
    }

    @Transactional
    @Override
    public void deleteById(long clientId) throws NotFoundException {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            clientRepository.save(client);
            personRepository.deleteById(client.getPerson().getId());
            LOGGER.info("Client with ID " + clientId + " was deleted");
        } else {
            String error = "There is no such client in database with ID " + clientId;
            LOGGER.error(error);
            throw new NotFoundException(error);
        }
    }

    @Transactional
    @Override
    public ClientDTO findById(long clientId) throws NotFoundException {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Client client;
        if (optionalClient.isPresent()) {
            client = optionalClient.get();
            LOGGER.info("Return client with ID " + clientId);
        } else {
            String error = "There is no such client with ID " + clientId;
            LOGGER.error(error);
            throw new NotFoundException(error);
        }
        return clientConverter.convertToDTO(client);
    }
}

