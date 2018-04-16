package dev3.bank.impl;

import dev3.bank.dao.interfaces.ClientDAO;
import dev3.bank.dao.interfaces.PersonDAO;
import dev3.bank.dao.utils.DataBase;
import dev3.bank.entity.Client;
import dev3.bank.entity.Person;
import dev3.bank.factory.DAOFactory;
import dev3.bank.interfaces.ClientService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public class ClientServiceImpl implements ClientService {
    private static ClientServiceImpl clientService;
    private PersonDAO personDAO;
    private ClientDAO clientDAO;

    private ClientServiceImpl() {
    }

    public static synchronized ClientServiceImpl getClientService() {
        if (clientService == null) {
            clientService = new ClientServiceImpl();
        }
        return clientService;

    }

    @Override
    public Client signIn(Person person) {
        return null;
    }

    @Override
    public void setDAO(DAOFactory daoFactory) {
        clientDAO = daoFactory.getClientDAO();
        personDAO = daoFactory.getPersonDAO();
    }


    @Override
    public Client registration(Person person) {
        Connection connection = DataBase.getConnection();
        Client addRes = null;
        try {
            connection.setAutoCommit(false);
            Client client = new Client();
            client.setPersonId(personDAO.add(person).getId());
            addRes = clientDAO.add(client);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                System.out.println("ROLLBACK");
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException e1) {
                System.out.println("SQL exception");
                e1.printStackTrace();
            }
            System.out.println("SQL exception");
            e.printStackTrace();
        }

        return addRes;
    }


    @Override
    public Collection<Client> getAllClients() {
        Collection<Client> clients = null;
        try {
            clients = clientDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }


}
