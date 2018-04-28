package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Account;
import com.netcracker.komarov.dao.entity.Transaction;
import com.netcracker.komarov.dao.factory.RepositoryFactory;
import com.netcracker.komarov.dao.repository.AccountRepository;
import com.netcracker.komarov.dao.repository.TransactionRepository;
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

@Service
public class TransactionServiceImpl implements TransactionService {
    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;
    private Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    public TransactionServiceImpl(RepositoryFactory repositoryFactory) {
        this.transactionRepository = repositoryFactory.getTransactionRepository();
        this.accountRepository = repositoryFactory.getAccountRepository();
    }

    @Transactional
    @Override
    public Collection<Transaction> showStories(long clientId) {
        logger.info("Return transaction story by client ID");
        return transactionRepository.findTransactionsByClientId(clientId);
    }

    @Transactional
    @Override
    public Transaction createTransaction(TransactionDTO transactionDTO) throws TransactionException {
        Transaction newTransaction = null;
        Optional<Account> optionalAccount = accountRepository.findById(transactionDTO.getAccountFromId());
        if (optionalAccount.isPresent()) {
            Account accountFrom = optionalAccount.get();
            if (accountFrom.getClient().getId() == transactionDTO.getClientId()) {
                Account accountTo = accountRepository.findById(transactionDTO.getAccountToId()).get();
                if (accountFrom.isLocked()) {
                    logger.info("Your account is lock");
                    throw new TransactionException("Your account is lock");
                }
                if (accountTo.isLocked()) {
                    logger.info("Other account is lock");
                    throw new TransactionException("Other account is lock");
                }
                double moneyFrom = accountFrom.getBalance();
                double transactionMoney = transactionDTO.getMoney();
                if (moneyFrom < transactionMoney) {
                    logger.info("Not enough money on your account");
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
            logger.info("Wrong input account from ID");
        }
        return newTransaction;
    }
}
