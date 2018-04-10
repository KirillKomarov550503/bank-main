package dev3.bank.dao.impl;

import dev3.bank.dao.interfaces.UnlockCardRequestDAO;
import dev3.bank.entity.UnlockCardRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    public UnlockCardRequest getById(long id) {
        UnlockCardRequest request = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM UnlockCardRequest WHERE id=?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                request = getUnlockCardRequest(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return request;
    }

    @Override
    public UnlockCardRequest update(UnlockCardRequest entity) {
        UnlockCardRequest request = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "UPDATE UnlockCardRequest SET card_id=? WHERE id=?");
            preparedStatement.setLong(1, entity.getCardId());
            preparedStatement.setLong(2, entity.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                request = getUnlockCardRequest(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return request;
    }

    @Override
    public void delete(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "DELETE FROM UnlockCardRequest WHERE id=?");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UnlockCardRequest add(UnlockCardRequest entity) {
        UnlockCardRequest request = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "INSERT INTO UnlockCardRequest(card_id) VALUES(?)");
            preparedStatement.setLong(1, entity.getCardId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                request = getUnlockCardRequest(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return request;
    }

    private UnlockCardRequest getUnlockCardRequest(ResultSet resultSet) throws SQLException {
        UnlockCardRequest request = new UnlockCardRequest();
        request.setId(resultSet.getLong("card_id"));
        request.setCardId(resultSet.getLong("card_id"));
        return request;
    }


    @Override
    public UnlockCardRequest getByCardId(long cardId) {
        UnlockCardRequest request = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM UnlockCardRequest WHERE card_id=?");
            preparedStatement.setLong(1, cardId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                request = getUnlockCardRequest(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return request;
    }
}
