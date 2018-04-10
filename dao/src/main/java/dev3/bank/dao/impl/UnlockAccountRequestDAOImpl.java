package dev3.bank.dao.impl;

import dev3.bank.dao.interfaces.UnlockAccountRequestDAO;
import dev3.bank.entity.UnlockAccountRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UnlockAccountRequestDAOImpl implements UnlockAccountRequestDAO {
    private static UnlockAccountRequestDAOImpl unlockAccountRequestDAO;
    private Connection connection;

    private UnlockAccountRequestDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public static synchronized UnlockAccountRequestDAOImpl getUnlcokAccountRequestDAO(Connection connection) {
        if (unlockAccountRequestDAO == null) {
            unlockAccountRequestDAO = new UnlockAccountRequestDAOImpl(connection);
        }
        return unlockAccountRequestDAO;
    }

    @Override
    public UnlockAccountRequest getById(long id) {
        UnlockAccountRequest request = null;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM UnlockAccountRequest WHERE id=?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                request = getUnlockAccountRequest(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return request;
    }

    @Override
    public UnlockAccountRequest update(UnlockAccountRequest entity) {
        UnlockAccountRequest request = null;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "UPDATE UnlockAccountRequest SET account_id=? WHERE id=?");
            preparedStatement.setLong(1, entity.getAccountId());
            preparedStatement.setLong(2, entity.getId());
            ResultSet resultSet  = preparedStatement.executeQuery();
            if(resultSet.next()){
                request = getUnlockAccountRequest(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return request;
    }

    @Override
    public void delete(long id) {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "DELETE FROM UnlockAccountRequest WHERE id=?");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UnlockAccountRequest add(UnlockAccountRequest entity) {
        UnlockAccountRequest request = null;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "INSERT INTO UnlockAccountRequest(account_id) VALUES(?)");
            preparedStatement.setLong(1, entity.getAccountId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                request = getUnlockAccountRequest(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return request;
    }

    private UnlockAccountRequest getUnlockAccountRequest(ResultSet resultSet) throws SQLException {
        UnlockAccountRequest unlockAccountRequest = new UnlockAccountRequest();
        unlockAccountRequest.setId(resultSet.getLong("id"));
        unlockAccountRequest.setAccountId(resultSet.getLong("account_id"));
        return unlockAccountRequest;
    }

    @Override
    public UnlockAccountRequest getByAccountId(long accountId) {
        UnlockAccountRequest request = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM UnlockAccountRequest WHERE account_id=?");
            preparedStatement.setLong(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                request = getUnlockAccountRequest(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return request;
    }
}
