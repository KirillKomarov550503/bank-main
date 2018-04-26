package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Transaction;
import com.netcracker.komarov.dao.utils.DataBase;
import com.netcracker.komarov.services.dto.TransactionDTO;
import com.netcracker.komarov.services.exception.TransactionException;
import com.netcracker.komarov.services.interfaces.AccountService;
import com.netcracker.komarov.services.interfaces.TransactionService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class TransactionServiceImplTest extends AbstractSpringTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;


    @Before
    public void init() {
        DataBase.initTable();
        DataBase.insertValues();
    }

    @After
    public void destroy() {
        DataBase.dropTable();
    }

    @Test
    public void showStories() {
        TransactionDTO transactionDTO1 = new TransactionDTO(1, 2, 13, 1);
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
        transactionDTO.setAccountFromId(1);
        transactionDTO.setAccountToId(2);
        transactionDTO.setClientId(3);
        transactionDTO.setMoney(23.5);
        try {
            Assert.assertNull(transactionService.createTransaction(transactionDTO));
        } catch (TransactionException e) {
            System.out.println("Transaction exception");
        }
    }
}