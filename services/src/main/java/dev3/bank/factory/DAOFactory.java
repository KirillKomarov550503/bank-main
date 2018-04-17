package dev3.bank.factory;

import dev3.bank.dao.interfaces.*;

public interface DAOFactory {
    AccountDAO getAccountDAO();

    AdminDAO getAdminDAO();

    CardDAO getCardDAO();

    ClientDAO getClientDAO();

    ClientNewsDAO getClientNewsDAO();

    NewsDAO getNewsDAO();

    PersonDAO getPersonDAO();

    TransactionDAO getTransactionDAO();

    UnlockAccountRequestDAO getUnlockAccountRequestDAO();

    UnlockCardRequestDAO getUnlockCardRequestDAO();

}
