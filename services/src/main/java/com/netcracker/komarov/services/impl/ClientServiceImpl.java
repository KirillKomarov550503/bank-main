package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Client;
import com.netcracker.komarov.dao.entity.Person;
import com.netcracker.komarov.dao.factory.RepositoryFactory;
import com.netcracker.komarov.dao.repository.ClientRepository;
import com.netcracker.komarov.dao.repository.PersonRepository;
import com.netcracker.komarov.services.interfaces.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class ClientServiceImpl implements ClientService {
    private PersonRepository personRepository;
    private ClientRepository clientRepository;
    private Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    public ClientServiceImpl(RepositoryFactory repositoryFactory) {
        this.personRepository = repositoryFactory.getPersonRepository();
        this.clientRepository = repositoryFactory.getClientRepository();
    }

    @Transactional
    @Override
    public Client signIn(Person person) {
        return null;
    }

    @Transactional
    @Override
    public Client registration(Person person) {
        Client client = new Client();
        client.getPerson().setId(personRepository.save(person).getId());
        logger.info("Registration of new client");
        return clientRepository.save(client);
    }

    @Transactional
    @Override
    public Collection<Client> getAllClients() {
        logger.info("Return all clients");
        return clientRepository.findAll();
    }
}

