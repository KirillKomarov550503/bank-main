package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Account;
import com.netcracker.komarov.dao.entity.Person;
import com.netcracker.komarov.dao.entity.Transaction;
import com.netcracker.komarov.dao.repository.AccountRepository;
import com.netcracker.komarov.dao.repository.PersonRepository;
import com.netcracker.komarov.dao.repository.TransactionRepository;
import com.netcracker.komarov.services.dto.converter.TransactionConverter;
import com.netcracker.komarov.services.dto.entity.TransactionDTO;
import com.netcracker.komarov.services.exception.LogicException;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.interfaces.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;
    private TransactionConverter transactionConverter;
    private PersonRepository personRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository,
                                  TransactionConverter transactionConverter, PersonRepository personRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.transactionConverter = transactionConverter;
        this.personRepository = personRepository;
    }

    private Collection<TransactionDTO> convertCollection(Collection<Transaction> transactions) {
        return transactions.stream()
                .map(transaction -> transactionConverter.convertToDTO(transaction))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isContain(long clientId, long transactionId) throws NotFoundException, LogicException {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        if (optionalTransaction.isPresent()) {
            Transaction transaction = optionalTransaction.get();
            long accountFromId = transaction.getAccountFromId();
            Account account = accountRepository.findById(accountFromId).get();
            if (account.getPerson().getId() != clientId) {
                String error = "You do not have access to show this transaction";
                LOGGER.error(error);
                throw new LogicException(error);
            }
        } else {
            String error = "No such transaction with ID " + transactionId;
            LOGGER.error(error);
            throw new NotFoundException(error);
        }
        return true;
    }

    @Transactional
    @Override
    public Collection<TransactionDTO> findTransactionsByClientId(long clientId) throws NotFoundException {
        Optional<Person> optionalClient = personRepository.findById(clientId);
        Collection<Transaction> transactions;
        if (optionalClient.isPresent()) {
            transactions = transactionRepository.findTransactionsByClientId(clientId);
            LOGGER.info("Return transaction story by client ID " + clientId);
        } else {
            String error = "There is no such client in database with ID " + clientId;
            LOGGER.error(error);
            throw new NotFoundException(error);
        }
        return convertCollection(transactions);
    }

    @Transactional
    @Override
    public TransactionDTO save(TransactionDTO transactionDTO, long clientId) throws LogicException,
            NotFoundException {
        Transaction transaction = transactionConverter.convertToEntity(transactionDTO);
        Transaction newTransaction;
        Optional<Person> optionalClient = personRepository.findById(clientId);
        if (!optionalClient.isPresent()) {
            String error = "There is no such client with ID " + clientId;
            LOGGER.error(error);
            throw new NotFoundException(error);
        }
        Optional<Account> optionalAccountFrom = accountRepository.findById(transaction.getAccountFromId());
        if (!optionalAccountFrom.isPresent()) {
            String error = "Not found account from with ID " + transactionDTO.getAccountFromId();
            LOGGER.error(error);
            throw new NotFoundException(error);
        }
        Account accountFrom = optionalAccountFrom.get();
        if (accountFrom.getPerson().getId() == clientId) {
            Optional<Account> optionalAccountTo = accountRepository.findById(transaction.getAccountToId());
            if (!optionalAccountTo.isPresent()) {
                String error = "There is no exist account to ID: " + transactionDTO.getAccountToId();
                LOGGER.error(error);
                throw new NotFoundException(error);
            }
            Account accountTo = optionalAccountTo.get();
            if (accountFrom.isLocked()) {
                String error = "Your account is lock";
                LOGGER.error(error);
                throw new LogicException(error);
            }
            if (accountTo.isLocked()) {
                String error = "Other account is lock";
                LOGGER.error(error);
                throw new LogicException(error);
            }
            double moneyFrom = accountFrom.getBalance();
            double transactionMoney = transaction.getMoney();
            if (moneyFrom < transactionMoney) {
                String error = "Not enough money on your account";
                LOGGER.error(error);
                throw new LogicException(error);
            }
            double moneyTo = accountTo.getBalance();
            accountFrom.setBalance(moneyFrom - transactionMoney);
            accountTo.setBalance(moneyTo + transactionMoney);
            Transaction trans = new Transaction();

            accountRepository.save(accountFrom);
            accountRepository.save(accountTo);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            trans.setDate(simpleDateFormat.format(new Date()));
            trans.setAccountFromId(accountFrom.getId());
            trans.setAccountToId(accountTo.getId());
            trans.setMoney(transactionMoney);
            newTransaction = transactionRepository.save(trans);
            LOGGER.info("Transaction was completed");
        } else {
            String error = "You don't own account with this ID: " + accountFrom.getId();
            LOGGER.error(error);
            throw new LogicException(error);
        }
        return transactionConverter.convertToDTO(newTransaction);
    }

    @Transactional
    @Override
    public TransactionDTO findById(long transactionId) throws NotFoundException {
        Optional<Transaction> optionalTransactionDTO = transactionRepository.findById(transactionId);
        Transaction transaction;
        if (optionalTransactionDTO.isPresent()) {
            transaction = optionalTransactionDTO.get();
            LOGGER.info("Return data about transaction with ID " + transactionId);
        } else {
            String error = "There is no such transaction in database with ID " + transactionId;
            LOGGER.error(error);
            throw new NotFoundException(error);
        }
        return transactionConverter.convertToDTO(transaction);
    }
}
