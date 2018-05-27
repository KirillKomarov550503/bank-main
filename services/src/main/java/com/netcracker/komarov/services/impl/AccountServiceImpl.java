package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Account;
import com.netcracker.komarov.dao.entity.Client;
import com.netcracker.komarov.dao.repository.AccountRepository;
import com.netcracker.komarov.dao.repository.ClientRepository;
import com.netcracker.komarov.services.dto.Status;
import com.netcracker.komarov.services.dto.converter.AccountConverter;
import com.netcracker.komarov.services.dto.entity.AccountDTO;
import com.netcracker.komarov.services.dto.entity.RequestDTO;
import com.netcracker.komarov.services.exception.LogicException;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.feign.RequestFeignClient;
import com.netcracker.komarov.services.interfaces.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private RequestFeignClient requestFeignClient;
    private ClientRepository clientRepository;
    private AccountConverter accountConverter;
    private Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, RequestFeignClient requestFeignClient,
                              ClientRepository clientRepository, AccountConverter accountConverter) {
        this.accountRepository = accountRepository;
        this.requestFeignClient = requestFeignClient;
        this.clientRepository = clientRepository;
        this.accountConverter = accountConverter;
    }

    private Collection<AccountDTO> convertCollection(Collection<Account> accounts) {
        return accounts.stream()
                .map(account -> accountConverter.convertToDTO(account))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isContain(long clientId, long accountId) throws NotFoundException {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        boolean contain;
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            contain = account.getClient().getId() == clientId;
        } else {
            String error = "No such account";
            logger.error(error);
            throw new NotFoundException(error);
        }
        return contain;
    }

    @Transactional
    @Override
    public AccountDTO lockAccount(long accountId) throws LogicException, NotFoundException {
        Account temp;
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            if (account.isLocked()) {
                String error = "This account is already locked";
                logger.error(error);
                throw new LogicException(error);
            } else {
                account.setLocked(true);
                temp = accountRepository.save(account);
                logger.info("Successful locking your account");
            }
        } else {
            String error = "There is no such account in database";
            logger.error(error);
            throw new NotFoundException(error);
        }
        return accountConverter.convertToDTO(temp);
    }

    @Transactional
    @Override
    public Collection<AccountDTO> getAllAccounts() {
        return convertCollection(accountRepository.findAll());
    }

    @Transactional
    @Override
    public AccountDTO unlockAccount(long accountId) throws LogicException, NotFoundException {
        Optional<RequestDTO> optionalRequest = requestFeignClient.findAllRequests().getBody()
                .stream()
                .filter(requestDTO -> Status.ACCOUNT.equals(requestDTO.getStatus()))
                .filter(request -> request.getEntityId() == accountId)
                .findFirst();
        Account res;
        if (optionalRequest.isPresent()) {
            RequestDTO request = optionalRequest.get();
            Account account = accountRepository.findById(request.getEntityId()).get();
            if (account.isLocked()) {
                account.setLocked(false);
                res = accountRepository.save(account);
                requestFeignClient.deleteById(request.getId());
                logger.info("Successful unlocking account with ID: " + account.getId());
            } else {
                String error = "This account is already unlocked";
                logger.error(error);
                throw new LogicException(error);
            }
        } else {
            String error = "There is no such account in requests";
            logger.error(error);
            throw new NotFoundException(error);
        }
        return accountConverter.convertToDTO(res);
    }

    @Transactional
    @Override
    public AccountDTO refill(long accountId) throws LogicException, NotFoundException {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        Account temp;
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            if (account.isLocked()) {
                String error = "This account is locking";
                logger.error(error);
                throw new LogicException(error);
            } else {
                double balance = account.getBalance();
                account.setBalance(balance + 100.0);
                temp = accountRepository.save(account);
                logger.info("Refill account");
            }
        } else {
            String error = "There is no such account in database";
            logger.error(error);
            throw new NotFoundException(error);
        }
        return accountConverter.convertToDTO(temp);
    }

    @Transactional
    @Override
    public Collection<AccountDTO> getAccountsByClientIdAndLock(long clientId, boolean lock) throws NotFoundException {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Collection<Account> accounts;
        if (optionalClient.isPresent()) {
            logger.info("Return all locked accounts by client ID");
            accounts = accountRepository.findAccountsByLockedAndClientId(clientId, lock);
        } else {
            String error = "There is no such client in database";
            logger.error(error);
            throw new NotFoundException(error);
        }
        return convertCollection(accounts);
    }

    @Transactional
    @Override
    public AccountDTO createAccount(AccountDTO accountDTO, long clientId) throws NotFoundException {
        Account account = accountConverter.convertToEntity(accountDTO);
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Client client;
        Account temp;
        if (optionalClient.isPresent()) {
            client = optionalClient.get();
            account.setClient(client);
            account.setBalance(0.0);
            account.setLocked(false);
            client.getAccounts().add(account);
            account.getClient().setId(clientId);
            temp = accountRepository.save(account);
            logger.info("Creation of account");
        } else {
            String error = "There is no such client in database";
            logger.error(error);
            throw new NotFoundException(error);
        }
        return accountConverter.convertToDTO(temp);
    }

    @Transactional
    @Override
    public void deleteById(long accountId) throws NotFoundException {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isPresent()) {
            accountRepository.deleteById(accountId);
            logger.info("Account was deleted");
        } else {
            String error = "There is no such account in database";
            logger.error(error);
            throw new NotFoundException(error);
        }
    }

    @Transactional
    @Override
    public AccountDTO findById(long accountId) throws NotFoundException {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        Account account;
        if (optionalAccount.isPresent()) {
            account = optionalAccount.get();
            logger.info("Return account");
        } else {
            String error = "There is no such account";
            logger.error(error);
            throw new NotFoundException(error);
        }
        return accountConverter.convertToDTO(account);
    }
}
