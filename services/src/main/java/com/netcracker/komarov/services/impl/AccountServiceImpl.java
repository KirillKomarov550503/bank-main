package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Account;
import com.netcracker.komarov.dao.entity.Client;
import com.netcracker.komarov.dao.entity.UnlockAccountRequest;
import com.netcracker.komarov.dao.factory.RepositoryFactory;
import com.netcracker.komarov.dao.repository.AccountRepository;
import com.netcracker.komarov.dao.repository.ClientRepository;
import com.netcracker.komarov.dao.repository.UnlockAccountRequestRepository;
import com.netcracker.komarov.services.dto.converter.AccountConverter;
import com.netcracker.komarov.services.dto.entity.AccountDTO;
import com.netcracker.komarov.services.interfaces.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private UnlockAccountRequestRepository unlockAccountRequestRepository;
    private ClientRepository clientRepository;
    private AccountConverter converter;
    private Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    public AccountServiceImpl(RepositoryFactory repositoryFactory, AccountConverter converter) {
        this.accountRepository = repositoryFactory.getAccountRepository();
        this.unlockAccountRequestRepository = repositoryFactory.getUnlockAccountRequestRepository();
        this.clientRepository = repositoryFactory.getClientRepository();
        this.converter = converter;
    }

    private Collection<AccountDTO> convertCollection(Collection<Account> accounts) {
        return accounts.stream()
                .map(account -> converter.convertToDTO(account))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public AccountDTO lockAccount(long accountId) {
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
        return converter.convertToDTO(temp);
    }

    @Transactional
    @Override
    public Collection<AccountDTO> getAllAccounts() {
        return convertCollection(accountRepository.findAll());
    }

    @Transactional
    @Override
    public AccountDTO unlockAccount(long accountId) {
        Optional<UnlockAccountRequest> optionalRequest = unlockAccountRequestRepository.findAll()
                .stream()
                .filter(unlockAccountRequest -> unlockAccountRequest.getAccount().getId() == accountId)
                .findFirst();
        Account res = null;
        if (optionalRequest.isPresent()) {
            Optional<Account> optionalAccount = accountRepository.findById(accountId);
            if (optionalAccount.isPresent()) {
                Account account = optionalAccount.get();
                account.setLocked(false);
                res = accountRepository.save(account);
                unlockAccountRequestRepository.deleteByAccountId(accountId);
                logger.info("Successful unlocking your account");
            }
        } else {
            logger.info("There is no such account in requests ");
        }
        return converter.convertToDTO(res);
    }

    @Transactional
    @Override
    public Collection<AccountDTO> getAllUnlockAccountRequest() {
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
        return convertCollection(accounts);
    }

    @Transactional
    @Override
    public AccountDTO refill(long accountId) {
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
        return converter.convertToDTO(temp);
    }

    @Transactional
    @Override
    public Collection<AccountDTO> getAccountsByClientIdAndLock(long clientId, boolean lock) {
        logger.info("Return all locked accounts by client ID");
        return convertCollection(accountRepository.findAccountsByLockedAndClientId(clientId, lock));
    }

    @Transactional
    @Override
    public AccountDTO createAccount(AccountDTO accountDTO, long clientId) {
        Account account = converter.convertToEntity(accountDTO);
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
        return converter.convertToDTO(temp);
    }
}
