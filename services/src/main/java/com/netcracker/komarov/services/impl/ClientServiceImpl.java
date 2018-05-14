package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Client;
import com.netcracker.komarov.dao.entity.Person;
import com.netcracker.komarov.dao.entity.Role;
import com.netcracker.komarov.dao.factory.RepositoryFactory;
import com.netcracker.komarov.dao.repository.ClientRepository;
import com.netcracker.komarov.dao.repository.PersonRepository;
import com.netcracker.komarov.services.dto.converter.ClientConverter;
import com.netcracker.komarov.services.dto.converter.PersonConverter;
import com.netcracker.komarov.services.dto.entity.ClientDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.interfaces.ClientService;
import com.netcracker.komarov.services.util.CustomPasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private CustomPasswordEncoder customPasswordEncoder;
    private Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    public ClientServiceImpl(RepositoryFactory repositoryFactory, ClientConverter clientConverter,
                             PersonConverter personConverter, CustomPasswordEncoder customPasswordEncoder) {
        this.clientRepository = repositoryFactory.getClientRepository();
        this.personRepository = repositoryFactory.getPersonRepository();
        this.clientConverter = clientConverter;
        this.personConverter = personConverter;
        this.customPasswordEncoder = customPasswordEncoder;
    }

    private Collection<ClientDTO> convertCollection(Collection<Client> clients) {
        return clients.stream()
                .map(client -> clientConverter.convertToDTO(client))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ClientDTO save(PersonDTO personDTO) {
        Person person = personConverter.convertToEntity(personDTO);
        person.setRole(Role.CLIENT);
        String password = person.getPassword();
        person.setPassword(customPasswordEncoder.encode(password));
        Client client = new Client();
        client.setPerson(person);
        person.setClient(client);
        logger.info("Registration of new client");
        return clientConverter.convertToDTO(clientRepository.save(client));
    }

    @Transactional
    @Override
    public Collection<ClientDTO> findAllClients() {
        logger.info("Return all clients");
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
            newPerson.setPassword(customPasswordEncoder.encode(password));
            Person oldPerson = oldClient.getPerson();
            newPerson.setId(oldPerson.getId());
            oldClient.setPerson(newPerson);
            resClient = clientRepository.saveAndFlush(oldClient);
            logger.info("Information about client was updated");
        } else {
            String error = "There is no such client in database";
            logger.error(error);
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
            client.setNewsSet(null);
            clientRepository.save(client);
            personRepository.deleteById(client.getPerson().getId());
            logger.info("Client was deleted");
        } else {
            String error = "There is no such client in database";
            logger.error(error);
            throw new NotFoundException(error);
        }
    }

    @Transactional
    @Override
    public ClientDTO findById(long clientId) throws NotFoundException {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Client client = null;
        if (optionalClient.isPresent()) {
            client = optionalClient.get();
            logger.info("Return client");
        } else {
            String error = "There is no such client";
            logger.error(error);
            throw new NotFoundException(error);
        }
        return clientConverter.convertToDTO(client);
    }
}

