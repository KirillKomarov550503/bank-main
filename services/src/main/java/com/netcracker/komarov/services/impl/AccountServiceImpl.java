package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Account;
import com.netcracker.komarov.dao.entity.Client;
import com.netcracker.komarov.dao.entity.UnlockAccountRequest;
import com.netcracker.komarov.dao.factory.RepositoryFactory;
import com.netcracker.komarov.dao.repository.AccountRepository;
import com.netcracker.komarov.dao.repository.ClientRepository;
import com.netcracker.komarov.dao.repository.UnlockAccountRequestRepository;
import com.netcracker.komarov.services.interfaces.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private UnlockAccountRequestRepository unlockAccountRequestRepository;
    private ClientRepository clientRepository;
    private Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    public AccountServiceImpl(RepositoryFactory repositoryFactory) {
        this.accountRepository = repositoryFactory.getAccountRepository();
        this.unlockAccountRequestRepository = repositoryFactory.getUnlockAccountRequestRepository();
        this.clientRepository = repositoryFactory.getClientRepository();
    }

    @Transactional
    @Override
    public Account lockAccount(long accountId) {
        Account temp = null;
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setLocked(true);
            temp = accountRepository.save(account);
            logger.info("Successful locking your account");
        } else {
            logger.info("There is no such account in database");
        }
        return temp;
    }

    @Transactional
    @Override
    public Collection<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Transactional
    @Override
    public void unlockAccount(long accountId) {
        Optional<UnlockAccountRequest> optionalRequest = unlockAccountRequestRepository.findAll()
                .stream()
                .filter(unlockAccountRequest -> unlockAccountRequest.getAccount().getId() == accountId)
                .findFirst();
        if (optionalRequest.isPresent()) {
            Optional<Account> optionalAccount = accountRepository.findById(accountId);
            if (optionalAccount.isPresent()) {
                Account account = optionalAccount.get();
                account.setLocked(false);
                accountRepository.save(account);
                unlockAccountRequestRepository.deleteByAccountId(accountId);
                logger.info("Successful unlocking your account");
            }
        } else {
            logger.info("There is no such account in requests ");
        }
    }

    @Transactional
    @Override
    public Collection<Account> getAllUnlockAccountRequest() {
        Collection<Account> accounts = new ArrayList<>();
        Collection<UnlockAccountRequest> requests = unlockAccountRequestRepository.findAll();
        for (UnlockAccountRequest request : requests) {
            accounts.add(request.getAccount());
        }
        if (accounts.size() == 0) {
            logger.info("There is no such request to unlock account");
        } else {
            logger.info("Return all request to unlock account");
        }
        return accounts;
    }

    @Transactional
    @Override
    public Account refill(long accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        Account account = null;
        Account temp = null;
        if (optionalAccount.isPresent()) {
            account = optionalAccount.get();
            double balance = account.getBalance();
            account.setBalance(balance + 100.0);
            temp = accountRepository.save(account);
            logger.info("Refill account");
        } else {
            logger.info("There is no such account in database");
        }
        return temp;
    }

    @Transactional
    @Override
    public Collection<Account> getLockAccounts(long clientId) {
        logger.info("Return all locked accounts by client ID");
        return accountRepository.findAccountsByLockedAndClientId(clientId, true);
    }

    @Transactional
    @Override
    public Collection<Account> getUnlockAccounts(long clientId) {
        logger.info("Return all unlocked accounts by client ID");
        return accountRepository.findAccountsByLockedAndClientId(clientId, false);
    }

    @Transactional
    @Override
    public Account createAccount(Account account, long clientId) {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Client client;
        Account temp = null;
        if (optionalClient.isPresent()) {
            client = optionalClient.get();
            account.setClient(client);
            client.getAccounts().add(account);
            account.getClient().setId(clientId);
            temp = accountRepository.save(account);
            logger.info("Creation of account");
        } else {
            logger.info("There is no such client in database");
        }
        return temp;
    }
}
