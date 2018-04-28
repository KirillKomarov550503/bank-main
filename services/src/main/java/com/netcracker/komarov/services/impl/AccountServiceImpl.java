package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Account;
import com.netcracker.komarov.dao.entity.UnlockAccountRequest;
import com.netcracker.komarov.dao.factory.RepositoryFactory;
import com.netcracker.komarov.dao.repository.AccountRepository;
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
    private Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    public AccountServiceImpl(RepositoryFactory repositoryFactory) {
        this.accountRepository = repositoryFactory.getAccountRepository();
        this.unlockAccountRequestRepository = repositoryFactory.getUnlockAccountRequestRepository();
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
            accountRepository.flush();
            logger.info("Successful locking your account");
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
        UnlockAccountRequest request;
        if (optionalRequest.isPresent()) {
            Account account;
            request = optionalRequest.get();
            Optional<Account> optionalAccount = accountRepository.findById(request.getId());
            if (optionalAccount.isPresent()) {
                account = optionalAccount.get();
                accountRepository.save(account);
                unlockAccountRequestRepository.deleteById(request.getId());
                unlockAccountRequestRepository.flush();
                logger.info("Successful unlocking your account");
            }
        }
    }

    @Transactional
    @Override
    public Collection<Account> getAllUnlockAccountRequest() {
        Collection<Account> accounts = new ArrayList<>();
        Collection<UnlockAccountRequest> requests = unlockAccountRequestRepository.findAll();
        for (UnlockAccountRequest request : requests) {
            accounts.add(accountRepository.findById(request.getAccount().getId()).get());
        }
        if (accounts.size() == 0) {
            logger.info("There is no request to unlock account");
        } else {
            logger.info("Return all request to unlock account");
        }
        return accounts;
    }

    @Transactional
    @Override
    public Account refill(long accountId) {
        Account account = accountRepository.findById(accountId).get();
        double balance = account.getBalance();
        account.setBalance(balance + 100.0);
        logger.info("Refill account");
        return accountRepository.saveAndFlush(account);
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
        account.getClient().setId(clientId);
        logger.info("Creation of account");
        return accountRepository.saveAndFlush(account);
    }
}
