package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.*;
import com.netcracker.komarov.dao.factory.RepositoryFactory;
import com.netcracker.komarov.dao.repository.ClientRepository;
import com.netcracker.komarov.dao.repository.NewsRepository;
import com.netcracker.komarov.dao.repository.PersonRepository;
import com.netcracker.komarov.services.dto.converter.ClientConverter;
import com.netcracker.komarov.services.dto.converter.PersonConverter;
import com.netcracker.komarov.services.dto.entity.ClientDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.interfaces.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private NewsRepository newsRepository;
    private Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    public ClientServiceImpl(RepositoryFactory repositoryFactory,
                             ClientConverter clientConverter, PersonConverter personConverter) {
        this.clientRepository = repositoryFactory.getClientRepository();
        this.personRepository = repositoryFactory.getPersonRepository();
        this.clientConverter = clientConverter;
        this.personConverter = personConverter;
        this.newsRepository = repositoryFactory.getNewsRepository();
    }

    private Collection<ClientDTO> convertCollection(Collection<Client> clients) {
        return clients.stream()
                .map(client -> clientConverter.convertToDTO(client))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ClientDTO signIn(PersonDTO personDTO) {
        return null;
    }

    @Transactional
    @Override
    public ClientDTO save(PersonDTO personDTO) {
        Person person = personConverter.convertToEntity(personDTO);
        person.setRole(Role.CLIENT);
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
    public ClientDTO update(ClientDTO clientDTO) {
        Client newClient = clientConverter.convertToEntity(clientDTO);
        Optional<Client> optionalClient = clientRepository.findById(clientDTO.getId());
        Client resClient = null;
        if (optionalClient.isPresent()) {
            Client oldClient = optionalClient.get();
            Person newPerson = newClient.getPerson();
            Person oldPerson = oldClient.getPerson();
            newPerson.setId(oldPerson.getId());
            oldClient.setPerson(newPerson);
            resClient = clientRepository.saveAndFlush(oldClient);
            logger.info("Information about client was updated");
        } else {
            logger.info("There is no such client in database");
        }
        return clientConverter.convertToDTO(resClient);
    }

    @Transactional
    @Override
    public void deleteById(long clientId) {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            client.setNewsSet(null);
            clientRepository.save(client);
            personRepository.deleteById(client.getPerson().getId());
            logger.info("Client was deleted");
        } else {
            logger.info("There is no such client in database");
        }
    }

    @Transactional
    @Override
    public ClientDTO findById(long clientId) {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Client client = null;
        if(optionalClient.isPresent()){
            client = optionalClient.get();
            logger.info("Return client");
        } else {
            logger.info("There is no such client");
        }
        return clientConverter.convertToDTO(client);
    }
}

