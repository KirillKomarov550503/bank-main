package com.netcracker.komarov.dao.factory;

import com.netcracker.komarov.dao.repository.*;

public interface RepositoryFactory {
    AccountRepository getAccountRepository();

    AdminRepository getAdminRepository();

    CardRepository getCardRepository();

    ClientRepository getClientRepository();

    NewsRepository getNewsRepository();

    PersonRepository getPersonRepository();

    TransactionRepository getTransactionRepository();

    RequestRepository getRequestRepository();
}

