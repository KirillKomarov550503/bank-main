package dev3.bank.dao.impl;

import dev3.bank.dao.interfaces.ClientDAO;
import dev3.bank.entity.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        Client client = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM Client WHERE id=?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                client = getClient(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    private Client getClient(ResultSet resultSet) throws SQLException {
        Client client = new Client();
        client.setId(resultSet.getLong("id"));
        client.setPersonId(resultSet.getLong("person_id"));
        return client;
    }

    @Override
    public Client update(Client entity) {
        Client client = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "UPDATE Client SET person_id=? WHERE id=?");
            preparedStatement.setLong(1, entity.getPersonId());
            preparedStatement.setLong(2, entity.getId());
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM Client WHERE id=?");
            preparedStatement.setLong(1, entity.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                client = getClient(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public void delete(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "DELETE FROM Client WHERE id=?");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Client add(Client entity) {
        Client client = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "INSERT INTO Client(person_id) VALUES(?)");
            preparedStatement.setLong(1, entity.getPersonId());
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM Client WHERE person_id=?");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                client = getClient(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public Collection<Client> getAll() {
        Collection<Client> clients = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM Client");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                clients.add(getClient(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }
}
