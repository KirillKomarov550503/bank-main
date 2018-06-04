package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Account;
import com.netcracker.komarov.dao.entity.Person;
import com.netcracker.komarov.dao.repository.AccountRepository;
import com.netcracker.komarov.dao.repository.PersonRepository;
import com.netcracker.komarov.services.dto.converter.AccountConverter;
import com.netcracker.komarov.services.dto.entity.AccountDTO;
import com.netcracker.komarov.services.exception.LogicException;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.interfaces.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:application-error.properties")
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private PersonRepository personRepository;
    private AccountConverter accountConverter;
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);
    private Environment environment;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, PersonRepository personRepository,
                              AccountConverter accountConverter, Environment environment) {
        this.accountRepository = accountRepository;
        this.personRepository = personRepository;
        this.accountConverter = accountConverter;
        this.environment = environment;
    }

    private Collection<AccountDTO> convertCollection(Collection<Account> accounts) {
        return accounts.stream()
                .map(account -> accountConverter.convertToDTO(account))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isContain(long personId, long accountId) throws NotFoundException {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        boolean contain;
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            contain = account.getPerson().getId() == personId;
        } else {
            String error = environment.getProperty("error.account.search") + accountId;
            LOGGER.error(error);
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
                LOGGER.error(error);
                throw new LogicException(error);
            } else {
                account.setLocked(true);
                temp = accountRepository.save(account);
                LOGGER.info("Successful locking account with ID " + accountId);
            }
        } else {
            String error = environment.getProperty("error.account.search") + accountId;
            LOGGER.error(error);
            throw new NotFoundException(error);
        }
        return accountConverter.convertToDTO(temp);
    }

    @Transactional
    @Override
    public Collection<AccountDTO> findAllAdmins() {
        LOGGER.info("Return all accounts");
        return convertCollection(accountRepository.findAll());
    }

    @Transactional
    @Override
    public AccountDTO unlockAccount(long accountId) throws LogicException, NotFoundException {
        Account res;
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            if (account.isLocked()) {
                account.setLocked(false);
                res = accountRepository.save(account);
                LOGGER.info("Successful unlock account with ID: " + account.getId());
            } else {
                String error = "This account is already unlocked";
                LOGGER.error(error);
                throw new LogicException(error);
            }
        } else {
            String error = environment.getProperty("error.account.search") + accountId;
            LOGGER.error(error);
            throw new NotFoundException(error);
        }
        return accountConverter.convertToDTO(res);
    }

    @Transactional
    @Override
    public AccountDTO refillAccount(long accountId) throws LogicException, NotFoundException {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        Account temp;
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            if (account.isLocked()) {
                String error = "This account is lock";
                LOGGER.error(error);
                throw new LogicException(error);
            } else {
                double balance = account.getBalance();
                account.setBalance(balance + 100.0);
                temp = accountRepository.save(account);
                LOGGER.info("Refill account with ID " + accountId);
            }
        } else {
            String error = environment.getProperty("error.account.search") + accountId;
            LOGGER.error(error);
            throw new NotFoundException(error);
        }
        return accountConverter.convertToDTO(temp);
    }

    @Transactional
    @Override
    public Collection<AccountDTO> findAccountsByClientIdAndLock(long personId, boolean lock) throws NotFoundException {
        Optional<Person> optionalClient = personRepository.findById(personId);
        Collection<Account> accounts;
        if (optionalClient.isPresent()) {
            LOGGER.info("Return all locked accounts by client ID " + personId);
            accounts = accountRepository.findAccountsByLockedAndPersonId(personId, lock);
        } else {
            String error = environment.getProperty("error.account.search") + personId;
            LOGGER.error(error);
            throw new NotFoundException(error);
        }
        return convertCollection(accounts);
    }

    @Transactional
    @Override
    public AccountDTO saveAccount(AccountDTO accountDTO, long personId) throws NotFoundException {
        Account account = accountConverter.convertToEntity(accountDTO);
        Optional<Person> optionalClient = personRepository.findById(personId);
        Person person;
        Account temp;
        if (optionalClient.isPresent()) {
            person = optionalClient.get();
            account.setPerson(person);
            account.setBalance(0.0);
            account.setLocked(false);
            person.getAccounts().add(account);
            account.getPerson().setId(personId);
            temp = accountRepository.save(account);
            LOGGER.info("Creation of account with ID " + temp.getId());
        } else {
            String error = environment.getProperty("error.account.search") + personId;
            LOGGER.error(error);
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
            LOGGER.info("Account with ID " + accountId + " was deleted");
        } else {
            String error = environment.getProperty("error.account.search") + accountId;
            LOGGER.error(error);
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
            LOGGER.info("Return account with ID " + accountId);
        } else {
            String error = environment.getProperty("error.account.search") + accountId;
            LOGGER.error(error);
            throw new NotFoundException(error);
        }
        return accountConverter.convertToDTO(account);
    }
}
