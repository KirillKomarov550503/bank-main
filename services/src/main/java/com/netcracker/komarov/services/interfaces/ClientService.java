package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.dao.entity.Client;
import com.netcracker.komarov.dao.entity.Person;

import java.util.Collection;

public interface ClientService {
    Client signIn(Person person);

    Client registration(Person person);

    Collection<Client> getAllClients();
}
