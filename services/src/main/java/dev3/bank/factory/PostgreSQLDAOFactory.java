package dev3.bank.factory;

import dev3.bank.dao.impl.*;
import dev3.bank.dao.interfaces.*;
import dev3.bank.dao.utils.DataBase;

import java.sql.Connection;

public class PostgreSQLDAOFactory implements DAOFactory {

    private static PostgreSQLDAOFactory postgreSQLDAOFactory;
    private Connection connection;

    private PostgreSQLDAOFactory() {
        this.connection = DataBase.getConnection();
    }

    public static synchronized PostgreSQLDAOFactory getPostgreSQLDAOFactory() {
        if (postgreSQLDAOFactory == null) {
            postgreSQLDAOFactory = new PostgreSQLDAOFactory();
        }
        return postgreSQLDAOFactory;
    }

    @Override
    public AccountDAO getAccountDAO() {
        return AccountDAOImpl.getAccountDAO(connection);
    }

    @Override
    public AdminDAO getAdminDAO() {
        return AdminDAOImpl.getAdminDAO(connection);
    }

    @Override
    public CardDAO getCardDAO() {
        return CardDAOImpl.getCardDAO(connection);
    }

    @Override
    public ClientDAO getClientDAO() {
        return ClientDAOImpl.getClientDAO(connection);
    }

    @Override
    public ClientNewsDAO getClientNewsDAO() {
        return ClientNewsDAOImpl.getClientNewsDAO(connection);
    }

    @Override
    public NewsDAO getNewsDAO() {
        return NewsDAOImpl.getNewsDAO(connection);
    }

    @Override
    public PersonDAO getPersonDAO() {
        return PersonDAOImpl.getPersonDAO(connection);
    }

    @Override
    public TransactionDAO getTransactionDAO() {
        return TransactionDAOImpl.getTransactionDAO(connection);
    }

    @Override
    public UnlockAccountRequestDAO getUnlockAccountRequestDAO() {
        return UnlockAccountRequestDAOImpl.getUnlcokAccountRequestDAO(connection);
    }

    @Override
    public UnlockCardRequestDAO getUnlockCardRequestDAO() {
        return UnlockCardRequestDAOImpl.getUnlockCardRequestDAO(connection);
    }
}
