package dev3.bank.dao.impl;

import dev3.bank.dao.interfaces.ClientDAO;
import dev3.bank.entity.Account;
import dev3.bank.entity.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class ClientDAOImpl implements ClientDAO {

    private static ClientDAOImpl clientDAO;

    private Connection connection;

    private ClientDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public static synchronized ClientDAOImpl getClientDAO(Connection connection) {
        if (clientDAO == null) {
            clientDAO = new ClientDAOImpl(connection);
        }
        return clientDAO;
    }

    @Override
    public Client getById(long id) {
        Client client = new Client();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM Client WHERE id=?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                client.setId(resultSet.getLong("id"));
                client.setPersonId(resultSet.getLong("person_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public Client update(Client entity) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public Client add(Client entity) {
        return null;
    }
}
