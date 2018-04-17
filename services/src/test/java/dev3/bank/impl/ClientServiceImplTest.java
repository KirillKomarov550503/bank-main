package dev3.bank.impl;

import dev3.bank.dao.utils.DataBase;
import dev3.bank.entity.Client;
import dev3.bank.entity.Person;
import dev3.bank.entity.Role;
import dev3.bank.factory.DAOFactory;
import dev3.bank.factory.PostgreSQLDAOFactory;
import dev3.bank.interfaces.ClientService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class ClientServiceImplTest {
    private ClientService clientService;

    @Before
    public void init() {
        DataBase.initTable();
        DataBase.insertValues();
        DAOFactory daoFactory = PostgreSQLDAOFactory.getPostgreSQLDAOFactory();
        clientService = ClientServiceImpl.getClientService();
        clientService.setDAO(daoFactory);
    }

    @After
    public void destroy() {
        DataBase.dropTable();
    }

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