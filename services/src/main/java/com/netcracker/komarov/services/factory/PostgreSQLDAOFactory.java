package com.netcracker.komarov.services.factory;

import com.netcracker.komarov.dao.impl.*;
import com.netcracker.komarov.dao.interfaces.*;
import com.netcracker.komarov.dao.utils.DataBase;
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
