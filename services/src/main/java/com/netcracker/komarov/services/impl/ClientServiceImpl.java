package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Client;
import com.netcracker.komarov.dao.entity.Person;
import com.netcracker.komarov.dao.entity.Role;
import com.netcracker.komarov.dao.factory.RepositoryFactory;
import com.netcracker.komarov.dao.repository.ClientRepository;
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
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;
    private ClientConverter clientConverter;
    private PersonConverter personConverter;
    private Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    public ClientServiceImpl(RepositoryFactory repositoryFactory,
                             ClientConverter clientConverter, PersonConverter personConverter) {
        this.clientRepository = repositoryFactory.getClientRepository();
        this.clientConverter = clientConverter;
        this.personConverter = personConverter;
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
    public ClientDTO registration(PersonDTO personDTO) {
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
    public Collection<ClientDTO> getAllClients() {
        logger.info("Return all clients");
        return convertCollection(clientRepository.findAll());
    }
}
