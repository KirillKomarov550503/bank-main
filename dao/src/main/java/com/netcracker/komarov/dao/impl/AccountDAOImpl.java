package com.netcracker.komarov.dao.impl;

import com.netcracker.komarov.dao.entity.Account;
import com.netcracker.komarov.dao.interfaces.AccountDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class AccountDAOImpl implements AccountDAO {

    private static AccountDAOImpl accountDAO;

    private Connection connection;

    private AccountDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public static synchronized AccountDAOImpl getAccountDAO(Connection connection) {
        if (accountDAO == null) {
            accountDAO = new AccountDAOImpl(connection);
        }
        return accountDAO;
    }

    @Override
    public Account getById(long id) throws SQLException {
        Account account = null;
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM ACCOUNT WHERE id=?");
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            account = getAccount(resultSet);
        }
        resultSet.close();
        preparedStatement.close();
        return account;
    }

    private Account getAccount(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getLong("id"));
        account.setBalance(resultSet.getDouble("balance"));
        account.setLocked(resultSet.getBoolean("locked"));
        account.setClientId(resultSet.getLong("client_Id"));
        return account;
    }

    @Override
    public Account update(Account entity) throws SQLException {
        Account account = null;
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "UPDATE ACCOUNT SET locked=?, balance=?, client_id=? WHERE id=?");
        preparedStatement.setBoolean(1, entity.isLocked());
        preparedStatement.setDouble(2, entity.getBalance());
        preparedStatement.setLong(3, entity.getClientId());
        preparedStatement.setLong(4, entity.getId());
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM Account WHERE id=?");
        preparedStatement.setLong(1, entity.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            account = getAccount(resultSet);
        }
        resultSet.close();
        preparedStatement.close();
        return account;
    }

    @Override
    public void delete(long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "DELETE FROM ACCOUNT WHERE id=?");
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public Account add(Account entity) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "INSERT INTO ACCOUNT(balance, locked, client_id) VALUES(?, ?, ?)" +
                "RETURNING id");
        preparedStatement.setDouble(1, entity.getBalance());
        preparedStatement.setBoolean(2, entity.isLocked());
        preparedStatement.setLong(3, entity.getClientId());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            entity.setId(resultSet.getLong("id"));
        }
        resultSet.close();
        preparedStatement.close();
        return entity;
    }

    @Override
    public Collection<Account> getAll() throws SQLException {
        Collection<Account> accounts = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM Account");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            accounts.add(getAccount(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return accounts;
    }

    @Override
    public Collection<Account> getAccountsByClientId(long clientId) throws SQLException {
        Collection<Account> accounts = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT  * FROM ACCOUNT WHERE client_id=?");
        preparedStatement.setLong(1, clientId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            accounts.add(getAccount(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return accounts;
    }


    @Override
    public Collection<Account> getLockedAccounts() throws SQLException {
        Collection<Account> accounts = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM ACCOUNT WHERE locked=TRUE");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            accounts.add(getAccount(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return accounts;
    }

    @Override
    public Collection<Account> getUnlockedAccounts() throws SQLException {
        Collection<Account> accounts = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM ACCOUNT WHERE locked=FALSE");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            accounts.add(getAccount(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return accounts;
    }

    @Override
    public Collection<Account> getLockedAccountsByClientId(long clientId) throws SQLException {
        Collection<Account> accounts = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM ACCOUNT WHERE locked=TRUE AND client_id=?");
        preparedStatement.setLong(1, clientId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            accounts.add(getAccount(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return accounts;
    }

    @Override
    public Collection<Account> getUnlockedAccountsByClientId(long clientId) throws SQLException {
        Collection<Account> accounts = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM ACCOUNT WHERE locked=FALSE AND client_id=?");
        preparedStatement.setLong(1, clientId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            accounts.add(getAccount(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return accounts;
    }
}
