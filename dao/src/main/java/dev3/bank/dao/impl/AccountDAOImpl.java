package dev3.bank.dao.impl;

import dev3.bank.dao.interfaces.AccountDAO;
import dev3.bank.entity.Account;

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
    public Collection<Account> getAccountsByClientId(long clientId) {
        Collection<Account> accounts = new ArrayList<>();
        try {
            PreparedStatement getAccounts = connection.prepareStatement("" +
                    "SELECT * FROM ACCOUNT WHERE client_id=?");
            getAccounts.setLong(1, clientId);
            ResultSet resultSet = getAccounts.executeQuery();
            parseResultSet(accounts, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    private void parseResultSet(Collection<Account> accounts, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Account account = new Account();
            account.setId(resultSet.getLong("id"));
            account.setClientId(resultSet.getLong("client_id"));
            account.setLocked(resultSet.getBoolean("locked"));
            account.setBalance(resultSet.getDouble("balance"));
            account.setAccountId(resultSet.getLong("account_id"));
            accounts.add(account);
        }
    }

    @Override
    public Collection<Account> getLockedAccounts() {
        Collection<Account> accounts = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM ACCOUNT WHERE locked=TRUE");
            ResultSet resultSet = preparedStatement.executeQuery();
            parseResultSet(accounts, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;

    }

    @Override
    public Collection<Account> getUnlockedAccounts() {
        Collection<Account> accounts = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM ACCOUNT WHERE locked=FALSE");
            ResultSet resultSet = preparedStatement.executeQuery();
            parseResultSet(accounts, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Collection<Account> getLockedAccountsByClientId(long clientId) {
        Collection<Account> accounts = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM ACCOUNT WHERE locked=TRUE AND client_id=?");
            preparedStatement.setLong(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            parseResultSet(accounts, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Collection<Account> getUnlockedAccountsByClientId(long clientId) {
        Collection<Account> accounts = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM ACCOUNT WHERE locked=FALSE AND client_id=?");
            preparedStatement.setLong(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            parseResultSet(accounts, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Account getById(long id) {
        Account account = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM ACCOUNT WHERE id=?");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                account.setId(id);
                account.setBalance(resultSet.getDouble("balance"));
                account.setLocked(resultSet.getBoolean("locked"));
                account.setClientId(resultSet.getLong("client_Id"));
                account.setAccountId(resultSet.getLong("account_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public Account update(Account entity) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "UPDATE ACCOUNT SET locked=?, balance=?, client_id=?, account_id=? WHERE id=?");
            preparedStatement.setBoolean(1, entity.isLocked());
            preparedStatement.setDouble(2, entity.getBalance());
            preparedStatement.setLong(3, entity.getClientId());
            preparedStatement.setLong(4, entity.getAccountId());
            preparedStatement.setLong(5, entity.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public void delete(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "DELETE FROM ACCOUNT WHERE id=?");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Account add(Account entity) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "INSERT INTO ACCOUNT(balance, locked, client_id, account_id) VALUES(?, ?, ?, ?)");
            preparedStatement.setDouble(1, entity.getBalance());
            preparedStatement.setBoolean(2, entity.isLocked());
            preparedStatement.setLong(3, entity.getClientId());
            preparedStatement.setLong(4, entity.getAccountId());
            preparedStatement.execute();
            PreparedStatement getStatement = connection.prepareStatement("SELECT * FROM ACCOUNT WHERE account_id=?");
            ResultSet resultSet = getStatement.executeQuery();
            if (resultSet.next()) {
                entity.setId(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public Account getByAccountId(long accountId) {
        Account account = new Account();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT id, locked, client_id, account_id FROM Account WHERE account_id=?");
            preparedStatement.setLong(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                account.setId(resultSet.getLong("id"));
                account.setAccountId(resultSet.getLong("account_id"));
                account.setBalance(resultSet.getDouble("balance"));
                account.setLocked(resultSet.getBoolean("locked"));
                account.setClientId(resultSet.getLong("client_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }
}
