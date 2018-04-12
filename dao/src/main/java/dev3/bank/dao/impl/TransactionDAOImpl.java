package dev3.bank.dao.impl;

import dev3.bank.dao.interfaces.TransactionDAO;
import dev3.bank.entity.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class TransactionDAOImpl implements TransactionDAO {
    private static TransactionDAOImpl transactionDAO;
    private Connection connection;

    private TransactionDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public static synchronized TransactionDAOImpl getTransactionDAO(Connection connection) {
        if (transactionDAO == null) {
            transactionDAO = new TransactionDAOImpl(connection);
        }
        return transactionDAO;
    }

    @Override
    public Transaction getById(long id) throws SQLException {
        Transaction transaction = null;
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM Transaction WHERE id=?");
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            transaction = getTransaction(resultSet);
        }
        resultSet.close();
        preparedStatement.close();
        return transaction;
    }

    @Override
    public Transaction update(Transaction entity) throws SQLException {
        Transaction transaction = null;
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "UPDATE Transaction SET date=?, from_id=?, to_id=?, money=? WHERE id=?");
        preparedStatement.setString(1, entity.getDate());
        preparedStatement.setLong(2, entity.getAccountFromId());
        preparedStatement.setLong(3, entity.getAccountToId());
        preparedStatement.setDouble(4, entity.getMoney());
        preparedStatement.setLong(5, entity.getId());
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM Transaction WHERE id=?");
        preparedStatement.setLong(1, entity.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            transaction = getTransaction(resultSet);
        }
        resultSet.close();
        preparedStatement.close();
        return transaction;
    }

    @Override
    public void delete(long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "DELETE FROM Transaction WHERE id=?");
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        preparedStatement.close();
    }


    @Override
    public Transaction add(Transaction entity) throws SQLException {
        Transaction transaction = null;
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "INSERT INTO Transaction(date, from_id, to_id, money) VALUES(?, ?, ?, ?)");
        preparedStatement.setString(1, entity.getDate());
        preparedStatement.setLong(2, entity.getAccountFromId());
        preparedStatement.setLong(3, entity.getAccountToId());
        preparedStatement.setDouble(4, entity.getMoney());
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM Transaction WHERE id=?");
        preparedStatement.setLong(1, entity.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            transaction = getTransaction(resultSet);
        }
        resultSet.close();
        preparedStatement.close();
        return transaction;
    }

    @Override
    public Collection<Transaction> getByAccountFromId(long accountFromId) throws SQLException {
        Collection<Transaction> transactions = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM Transaction WHERE from_id=?");
        preparedStatement.setLong(1, accountFromId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            transactions.add(getTransaction(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return transactions;
    }

    @Override
    public Collection<Transaction> getByAccountToId(long accountToId) throws SQLException {
        Collection<Transaction> transactions = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM Transaction WHERE to_id=?");
        preparedStatement.setLong(1, accountToId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            transactions.add(getTransaction(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return transactions;
    }

    private Transaction getTransaction(ResultSet resultSet) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setId(resultSet.getLong("id"));
        transaction.setDate(resultSet.getString("date"));
        transaction.setAccountFromId(resultSet.getLong("from_id"));
        transaction.setAccountToId(resultSet.getLong("to_id"));
        transaction.setMoney(resultSet.getDouble("money"));
        return transaction;
    }

    @Override
    public Collection<Transaction> getByClientId(long clientId) throws SQLException {
        Collection<Transaction> transactions = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM Transaction WHERE from_id IN (SELECT id FROM Account WHERE client_id=?)");
        preparedStatement.setLong(1, clientId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            transactions.add(getTransaction(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return transactions;
    }

    @Override
    public Collection<Transaction> getAll() throws SQLException {
        Collection<Transaction> transactions = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM Transaction");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            transactions.add(getTransaction(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return transactions;
    }
}
