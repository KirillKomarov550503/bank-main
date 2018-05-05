package com.netcracker.komarov.dao.factory;

import com.netcracker.komarov.dao.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostgreSQLRepositoryFactory implements RepositoryFactory {
    private AccountRepository accountRepository;
    private AdminRepository adminRepository;
    private CardRepository cardRepository;
    private ClientRepository clientRepository;
    private NewsRepository newsRepository;
    private PersonRepository personRepository;
    private TransactionRepository transactionRepository;
    private RequestRepository requestRepository;

    @Autowired
    public PostgreSQLRepositoryFactory(
            AccountRepository accountRepository, AdminRepository adminRepository, CardRepository cardRepository,
            ClientRepository clientRepository, NewsRepository newsRepository, PersonRepository personRepository,
            TransactionRepository transactionRepository, RequestRepository requestRepository) {
        this.accountRepository = accountRepository;
        this.adminRepository = adminRepository;
        this.cardRepository = cardRepository;
        this.clientRepository = clientRepository;
        this.newsRepository = newsRepository;
        this.personRepository = personRepository;
        this.transactionRepository = transactionRepository;
        this.requestRepository = requestRepository;
    }

    @Override
    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    @Override
    public AdminRepository getAdminRepository() {
        return adminRepository;
    }

    @Override
    public CardRepository getCardRepository() {
        return cardRepository;
    }

    @Override
    public ClientRepository getClientRepository() {
        return clientRepository;
    }

    @Override
    public NewsRepository getNewsRepository() {
        return newsRepository;
    }

    @Override
    public PersonRepository getPersonRepository() {
        return personRepository;
    }

    @Override
    public TransactionRepository getTransactionRepository() {
        return transactionRepository;
    }

    @Override
    public RequestRepository getRequestRepository() {
        return requestRepository;
    }
}

