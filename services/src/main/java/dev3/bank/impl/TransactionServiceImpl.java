package dev3.bank.impl;

import dev3.bank.dao.interfaces.AccountDAO;
import dev3.bank.dao.interfaces.TransactionDAO;
import dev3.bank.dao.utils.DataBase;
import dev3.bank.dto.TransactionDTO;
import dev3.bank.entity.Account;
import dev3.bank.entity.Transaction;
import dev3.bank.exception.TransactionException;
import dev3.bank.factory.DAOFactory;
import dev3.bank.interfaces.TransactionService;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@Service
public class TransactionServiceImpl implements TransactionService {
    private TransactionDAO transactionDAO;
    private AccountDAO accountDAO;
    private static TransactionServiceImpl transactionService;

    private TransactionServiceImpl() {
    }

    public static synchronized TransactionServiceImpl getTransactionService() {
        if (transactionService == null) {
            transactionService = new TransactionServiceImpl();
        }
        return transactionService;
    }

    @Override
    public void setDAO(DAOFactory daoFactory) {
        transactionDAO = daoFactory.getTransactionDAO();
        accountDAO = daoFactory.getAccountDAO();
    }

    @Override
    public Collection<Transaction> showStories(long clientId) {
        Collection<Transaction> transactions = null;
        try {
            transactions = transactionDAO.getByClientId(clientId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    @Override
    public Transaction createTransaction(TransactionDTO transactionDTO) throws TransactionException {
        Connection connection = DataBase.getConnection();
        Transaction newTransaction = null;
        try {
            Account accountFrom = accountDAO.getById(transactionDTO.getAccountFromId());
            if (accountFrom.getClientId() == transactionDTO.getClientId()) {
                Account accountTo = accountDAO.getById(transactionDTO.getAccountToId());
                if (accountFrom.isLocked()) {
                    throw new TransactionException("Your account is lock");
                }
                if (accountTo.isLocked()) {
                    throw new TransactionException("Other account is lock");
                }
                double moneyFrom = accountFrom.getBalance();
                double transactionMoney = transactionDTO.getMoney();
                if (moneyFrom < transactionMoney) {
                    throw new TransactionException("Not enough money on your account");
                }
                double moneyTo = accountTo.getBalance();
                accountFrom.setBalance(moneyFrom - transactionMoney);
                accountTo.setBalance(moneyTo + transactionMoney);
                Transaction transaction = new Transaction();

                connection.setAutoCommit(false);
                accountDAO.update(accountFrom);
                accountDAO.update(accountTo);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                transaction.setDate(simpleDateFormat.format(new Date()));
                transaction.setAccountFromId(accountFrom.getId());
                transaction.setAccountToId(accountTo.getId());
                transaction.setMoney(transactionMoney);
                newTransaction = transactionDAO.add(transaction);
                connection.commit();
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return newTransaction;
    }
}
