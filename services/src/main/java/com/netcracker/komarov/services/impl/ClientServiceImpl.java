package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Client;
import com.netcracker.komarov.dao.entity.Person;
import com.netcracker.komarov.dao.interfaces.ClientDAO;
import com.netcracker.komarov.dao.interfaces.PersonDAO;
import com.netcracker.komarov.dao.utils.DataBase;
import com.netcracker.komarov.services.factory.DAOFactory;
import com.netcracker.komarov.services.interfaces.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

@Service
public class ClientServiceImpl implements ClientService {
    private PersonDAO personDAO;
    private ClientDAO clientDAO;

    @Autowired
    public ClientServiceImpl(DAOFactory daoFactory) {
        this.personDAO = daoFactory.getPersonDAO();
        this.clientDAO = daoFactory.getClientDAO();
    }

    @Override
    public Client signIn(Person person) {
        return null;
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
