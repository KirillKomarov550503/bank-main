package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Account;
import com.netcracker.komarov.dao.entity.Transaction;
import com.netcracker.komarov.dao.factory.RepositoryFactory;
import com.netcracker.komarov.dao.repository.AccountRepository;
import com.netcracker.komarov.dao.repository.TransactionRepository;
import com.netcracker.komarov.services.dto.converter.TransactionConverter;
import com.netcracker.komarov.services.dto.entity.TransactionDTO;
import com.netcracker.komarov.services.exception.TransactionException;
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
    private Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    public TransactionServiceImpl(RepositoryFactory repositoryFactory, TransactionConverter transactionConverter) {
        this.transactionRepository = repositoryFactory.getTransactionRepository();
        this.accountRepository = repositoryFactory.getAccountRepository();
        this.transactionConverter = transactionConverter;
    }

    private Collection<TransactionDTO> convertCollection(Collection<Transaction> transactions) {
        return transactions.stream()
                .map(transaction -> transactionConverter.convertToDTO(transaction))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Collection<TransactionDTO> showStories(long clientId) {
        logger.info("Return transaction story by client ID");
        return convertCollection(transactionRepository.findTransactionsByClientId(clientId));
    }

    @Transactional
    @Override
    public TransactionDTO createTransaction(TransactionDTO transactionDTO, long clientId) throws TransactionException {
        Transaction newTransaction = null;
        Optional<Account> optionalAccount = accountRepository.findById(transactionDTO.getAccountFromId());
        if (optionalAccount.isPresent()) {
            Account accountFrom = optionalAccount.get();
            if (accountFrom.getClient().getId() == clientId) {
                Account accountTo = accountRepository.findById(transactionDTO.getAccountToId()).get();
                if (accountFrom.isLocked()) {
                    logger.error("Your account is lock");
                    throw new TransactionException("Your account is lock");
                }
                if (accountTo.isLocked()) {
                    logger.error("Other account is lock");
                    throw new TransactionException("Other account is lock");
                }
                double moneyFrom = accountFrom.getBalance();
                double transactionMoney = transactionDTO.getMoney();
                if (moneyFrom < transactionMoney) {
                    logger.error("Not enough money on your account");
                    throw new TransactionException("Not enough money on your account");
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
            }
        } else {
            logger.error("Wrong input account from ID");
        }
        return transactionConverter.convertToDTO(newTransaction);
    }

    @Transactional
    @Override
    public TransactionDTO findById(long transactionId) {
        Optional<Transaction> optionalTransactionDTO = transactionRepository.findById(transactionId);
        Transaction transaction = null;
        if (optionalTransactionDTO.isPresent()) {
            transaction = optionalTransactionDTO.get();
            logger.info("Return data about transaction");
        } else {
            logger.error("There is no such transaction in database");
        }
        return transactionConverter.convertToDTO(transaction);
    }
}
