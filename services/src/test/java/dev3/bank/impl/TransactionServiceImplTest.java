package dev3.bank.impl;

import dev3.bank.dao.utils.DataBase;
import dev3.bank.dto.TransactionDTO;
import dev3.bank.entity.Transaction;
import dev3.bank.exception.TransactionException;
import dev3.bank.factory.DAOFactory;
import dev3.bank.factory.PostgreSQLDAOFactory;
import dev3.bank.interfaces.AccountService;
import dev3.bank.interfaces.TransactionService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.xml.crypto.Data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.*;

public class TransactionServiceImplTest {
    private TransactionService transactionService;
    private AccountService accountService;


    @Before
    public void init() {
        DataBase.dropTable();
        DataBase.initTable();
        DataBase.insertValues();
        DAOFactory daoFactory = PostgreSQLDAOFactory.getPostgreSQLDAOFactory();
        transactionService = TransactionServiceImpl.getTransactionService();
        transactionService.setDAO(daoFactory);
        accountService = AccountServiceImpl.getAccountService();
        accountService.setDAO(daoFactory);
    }

    @Test
    public void showStories() {
        TransactionDTO transactionDTO1 = new TransactionDTO(1,2, 13, 1);
        TransactionDTO transactionDTO2 = new TransactionDTO(3, 1, 24.9, 3);
        TransactionDTO transactionDTO3 = new TransactionDTO(2, 3, 20, 1);
        accountService.refill(1);
        accountService.refill(3);
        accountService.refill(2);
        try {
            transactionService.createTransaction(transactionDTO1);
            transactionService.createTransaction(transactionDTO2);
            transactionService.createTransaction(transactionDTO3);
        } catch (TransactionException e) {
            System.out.println("TransactionException");
        }
        Collection<Transaction> transactions = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String dateFormat = simpleDateFormat.format(new Date());
        Transaction transaction1 = new Transaction(1, dateFormat, 1, 2, 13);
        Transaction transaction3 = new Transaction(3, dateFormat, 2, 3, 20);
        transactions.add(transaction1);
        transactions.add(transaction3);
        Assert.assertEquals(transactions, transactionService.showStories(1));
    }

    @Test
    public void createTransaction() throws TransactionException {
        TransactionDTO transactionDTO = new TransactionDTO();
        accountService.refill(3);
        transactionDTO.setAccountFromId(3);
        transactionDTO.setAccountToId(2);
        transactionDTO.setClientId(3);
        transactionDTO.setMoney(23.5);
        Transaction transaction = new Transaction();
        transaction.setId(1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        transaction.setId(1);
        transaction.setDate(simpleDateFormat.format(new Date()));
        transaction.setAccountFromId(3);
        transaction.setAccountToId(2);
        transaction.setMoney(23.5);
        try{
            Assert.assertEquals(transaction, transactionService.createTransaction(transactionDTO));
        } catch (TransactionException e){
            System.out.println("Transaction exception");
        }
    }
}