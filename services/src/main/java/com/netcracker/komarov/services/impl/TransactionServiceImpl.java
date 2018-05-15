package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Account;
import com.netcracker.komarov.dao.entity.Client;
import com.netcracker.komarov.dao.entity.Transaction;
import com.netcracker.komarov.dao.factory.RepositoryFactory;
import com.netcracker.komarov.dao.repository.AccountRepository;
import com.netcracker.komarov.dao.repository.ClientRepository;
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
    private ClientRepository clientRepository;
    private Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    public TransactionServiceImpl(RepositoryFactory repositoryFactory, TransactionConverter transactionConverter) {
        this.transactionRepository = repositoryFactory.getTransactionRepository();
        this.accountRepository = repositoryFactory.getAccountRepository();
        this.clientRepository = repositoryFactory.getClientRepository();
        this.transactionConverter = transactionConverter;
    }

    private Collection<TransactionDTO> convertCollection(Collection<Transaction> transactions) {
        return transactions.stream()
                .map(transaction -> transactionConverter.convertToDTO(transaction))
                .collect(Collectors.toList());
    }

    @Override
    public boolean contain(long clientId, long transactionId) throws NotFoundException {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        boolean contain;
        if (optionalTransaction.isPresent()) {
            Transaction transaction = optionalTransaction.get();
            contain = transaction.getAccountFromId() == transactionId;
        } else {
            String error = "No such transaction";
            logger.error(error);
            throw new NotFoundException(error);
        }
        return contain;
    }

    @Transactional
    @Override
    public Collection<TransactionDTO> showStories(long clientId) throws NotFoundException {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Collection<Transaction> transactions;
        if (optionalClient.isPresent()) {
            transactions = transactionRepository.findTransactionsByClientId(clientId);
            logger.info("Return transaction story by client ID");
        } else {
            String error = "There is no such client in database";
            logger.error(error);
            throw new NotFoundException(error);
        }
        return convertCollection(transactions);
    }

    @Transactional
    @Override
    public TransactionDTO createTransaction(TransactionDTO transactionDTO, long clientId) throws LogicException,
            NotFoundException {
        Transaction newTransaction = null;
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        if (!optionalClient.isPresent()) {
            String error = "There is no such client";
            logger.error(error);
            throw new NotFoundException(error);
        }
        Optional<Account> optionalAccountFrom = accountRepository.findById(transactionDTO.getAccountFromId());
        if (!optionalAccountFrom.isPresent()) {
            String error = "Not found account from ID";
            logger.error(error);
            throw new NotFoundException(error);
        }
        Account accountFrom = optionalAccountFrom.get();
        if (accountFrom.getClient().getId() == clientId) {
            Optional<Account> optionalAccountTo = accountRepository.findById(transactionDTO.getAccountToId());
            if (!optionalAccountTo.isPresent()) {
                String error = "There is no exist account to ID: " + transactionDTO.getAccountToId();
                logger.error(error);
                throw new NotFoundException(error);
            }
            Account accountTo = optionalAccountTo.get();
            if (accountFrom.isLocked()) {
                String error = "Your account is lock";
                logger.error(error);
                throw new LogicException(error);
            }
            if (accountTo.isLocked()) {
                String error = "Other account is lock";
                logger.error(error);
                throw new LogicException(error);
            }
            double moneyFrom = accountFrom.getBalance();
            double transactionMoney = transactionDTO.getMoney();
            if (moneyFrom < transactionMoney) {
                String error = "Not enough money on your account";
                logger.error(error);
                throw new LogicException(error);
            }
            double moneyTo = accountTo.getBalance();
            accountFrom.setBalance(moneyFrom - transactionMoney);
            accountTo.setBalance(moneyTo + transactionMoney);
            Transaction transaction = new Transaction();

            accountRepository.save(accountFrom);
            accountRepository.save(accountTo);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            transaction.setDate(simpleDateFormat.format(new Date()));
            transaction.setAccountFromId(accountFrom.getId());
            transaction.setAccountToId(accountTo.getId());
            transaction.setMoney(transactionMoney);
            newTransaction = transactionRepository.save(transaction);
            logger.info("Transaction was completed");
        } else {
            String error = "You don't own account with this ID: " + accountFrom.getId();
            logger.error(error);
            throw new LogicException(error);
        }
        return transactionConverter.convertToDTO(newTransaction);
    }

    @Transactional
    @Override
    public TransactionDTO findById(long transactionId) throws NotFoundException {
        Optional<Transaction> optionalTransactionDTO = transactionRepository.findById(transactionId);
        Transaction transaction = null;
        if (optionalTransactionDTO.isPresent()) {
            transaction = optionalTransactionDTO.get();
            logger.info("Return data about transaction");
        } else {
            String error = "There is no such transaction in database";
            logger.error(error);
            throw new NotFoundException(error);
        }
        return transactionConverter.convertToDTO(transaction);
    }
}
