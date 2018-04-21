package dev3.bank.factory;

import dev3.bank.dao.impl.*;
import dev3.bank.dao.interfaces.*;
import dev3.bank.dao.utils.DataBase;
import org.springframework.stereotype.Component;

@Component
public class PostgreSQLDAOFactory implements DAOFactory {

    @Override
    public AccountDAO getAccountDAO() {
        return AccountDAOImpl.getAccountDAO(DataBase.getConnection());
    }

    @Override
    public AdminDAO getAdminDAO() {
        return AdminDAOImpl.getAdminDAO(DataBase.getConnection());
    }

    @Override
    public CardDAO getCardDAO() {
        return CardDAOImpl.getCardDAO(DataBase.getConnection());
    }

    @Override
    public ClientDAO getClientDAO() {
        return ClientDAOImpl.getClientDAO(DataBase.getConnection());
    }

    @Override
    public ClientNewsDAO getClientNewsDAO() {
        return ClientNewsDAOImpl.getClientNewsDAO(DataBase.getConnection());
    }

    @Override
    public NewsDAO getNewsDAO() {
        return NewsDAOImpl.getNewsDAO(DataBase.getConnection());
    }

    @Override
    public PersonDAO getPersonDAO() {
        return PersonDAOImpl.getPersonDAO(DataBase.getConnection());
    }

    @Override
    public TransactionDAO getTransactionDAO() {
        return TransactionDAOImpl.getTransactionDAO(DataBase.getConnection());
    }

    @Override
    public UnlockAccountRequestDAO getUnlockAccountRequestDAO() {
        return UnlockAccountRequestDAOImpl.getUnlcokAccountRequestDAO(DataBase.getConnection());
    }

    @Override
    public UnlockCardRequestDAO getUnlockCardRequestDAO() {
        return UnlockCardRequestDAOImpl.getUnlockCardRequestDAO(DataBase.getConnection());
    }
}
