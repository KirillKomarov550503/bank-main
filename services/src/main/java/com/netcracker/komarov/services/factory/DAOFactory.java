package com.netcracker.komarov.services.factory;

import com.netcracker.komarov.dao.interfaces.*;

public interface DAOFactory {
    AccountDAO getAccountDAO();

    AdminDAO getAdminDAO();

    CardDAO getCardDAO();

    ClientDAO getClientDAO();

    ClientNewsDAO getClientNewsDAO();

    NewsDAO getNewsDAO();

    PersonDAO getPersonDAO();

    TransactionDAO getTransactionDAO();

    RequestDAO getRequestDAO();
}
