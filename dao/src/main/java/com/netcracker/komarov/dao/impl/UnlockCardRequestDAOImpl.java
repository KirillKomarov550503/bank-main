package com.netcracker.komarov.dao.impl;

import com.netcracker.komarov.dao.entity.UnlockCardRequest;
import com.netcracker.komarov.dao.interfaces.UnlockCardRequestDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class UnlockCardRequestDAOImpl implements UnlockCardRequestDAO {
    private static UnlockCardRequestDAOImpl unlockCardRequestDAO;
    private Connection connection;

    private UnlockCardRequestDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public static synchronized UnlockCardRequestDAOImpl getUnlockCardRequestDAO(Connection connection) {
        if (unlockCardRequestDAO == null) {
            unlockCardRequestDAO = new UnlockCardRequestDAOImpl(connection);
        }
        return unlockCardRequestDAO;
    }

    @Override
    public UnlockCardRequest getById(long id) throws SQLException {
        UnlockCardRequest request = null;
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM UnlockCardRequest WHERE id=?");
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            request = getUnlockCardRequest(resultSet);
        }
        resultSet.close();
        preparedStatement.close();
        return request;
    }

    @Override
    public UnlockCardRequest update(UnlockCardRequest entity) throws SQLException {
        UnlockCardRequest request = null;
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "UPDATE UnlockCardRequest SET card_id=? WHERE id=?");
        preparedStatement.setLong(1, entity.getCardId());
        preparedStatement.setLong(2, entity.getId());
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM UnlockCardRequest WHERE id=?");
        preparedStatement.setLong(1, entity.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            request = getUnlockCardRequest(resultSet);
        }
        resultSet.close();
        preparedStatement.close();
        return request;
    }

    @Override
    public void delete(long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "DELETE FROM UnlockCardRequest WHERE id=?");
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public UnlockCardRequest add(UnlockCardRequest entity) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "INSERT INTO UnlockCardRequest(card_id) VALUES(?) RETURNING id");
        preparedStatement.setLong(1, entity.getCardId());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            entity.setId(resultSet.getLong("id"));
        }
        resultSet.close();
        preparedStatement.close();
        return entity;
    }

    private UnlockCardRequest getUnlockCardRequest(ResultSet resultSet) throws SQLException {
        UnlockCardRequest request = new UnlockCardRequest();
        request.setId(resultSet.getLong("id"));
        request.setCardId(resultSet.getLong("card_id"));
        return request;
    }


    @Override
    public UnlockCardRequest getByCardId(long cardId) throws SQLException {
        UnlockCardRequest request = null;
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM UnlockCardRequest WHERE card_id=?");
        preparedStatement.setLong(1, cardId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            request = getUnlockCardRequest(resultSet);
        }
        resultSet.close();
        preparedStatement.close();
        return request;
    }

    @Override
    public Collection<UnlockCardRequest> getAll() throws SQLException {
        Collection<UnlockCardRequest> requests = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM UnlockCardRequest");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            requests.add(getUnlockCardRequest(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return requests;
    }
}