package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Client;
import com.netcracker.komarov.dao.entity.Person;
import com.netcracker.komarov.dao.entity.Role;
import com.netcracker.komarov.dao.utils.DataBase;
import com.netcracker.komarov.services.interfaces.ClientService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;

public class ClientServiceImplTest extends AbstractSpringTest {

    @Autowired
    private ClientService clientService;

    @Test
    public void signIn() {
        Assert.assertEquals(null, clientService.signIn(null));
    }

    @Test
    public void registration() {
        Person person = new Person(0, "Dmitrya", "Medvedev", 4234, Role.CLIENT,
                null, null, 2);
        Client client = new Client(4, 5);
        Assert.assertEquals(client, clientService.registration(person));
    }

    @Test
    public void getAllClients() {
        Collection<Client> clients = new ArrayList<>();
        Client client1 = new Client(1, 1);
        Client client2 = new Client(2, 2);
        Client client3 = new Client(3, 3);
        clients.add(client1);
        clients.add(client2);
        clients.add(client3);
        Assert.assertEquals(clients, clientService.getAllClients());
    }
}