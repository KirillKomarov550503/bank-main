package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.TestConfig;
import com.netcracker.komarov.dao.entity.Role;
import com.netcracker.komarov.services.dto.entity.AccountDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.dto.entity.TransactionDTO;
import com.netcracker.komarov.services.exception.LogicException;
import com.netcracker.komarov.services.interfaces.AccountService;
import com.netcracker.komarov.services.interfaces.PersonService;
import com.netcracker.komarov.services.interfaces.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TransactionServiceImplTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PersonService personService;

    @Before
    public void init() {
        personService.save(new PersonDTO(0L, "Kirill", "Komarov",
                "1", "1", "Optimist", "qwerty", Role.CLIENT));
        personService.save(new PersonDTO(0L, "Vlad", "M",
                "2", "2", "Sloupok", "0000000", Role.CLIENT));
        accountService.saveAccount(new AccountDTO(false, 0.0), 1);
        accountService.saveAccount(new AccountDTO(false, 0.0), 2);
        accountService.saveAccount(new AccountDTO(false, 0.0), 1);
        accountService.refillAccount(1);
    }

    @Test
    public void showStories() throws LogicException {
        accountService.refillAccount(2);
        accountService.refillAccount(3);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String date = simpleDateFormat.format(new Date());
        TransactionDTO transactionDTO1 = new TransactionDTO(0L, "2", "1", "13", date);
        TransactionDTO transactionDTO2 = new TransactionDTO(0L, "1", "3", "24.9", date);
        TransactionDTO transactionDTO3 = new TransactionDTO(0L, "3", "2", "60", date);
        transactionService.save(transactionDTO1, 2);
        transactionService.save(transactionDTO2, 1);
        transactionService.save(transactionDTO3, 1);
        Collection<TransactionDTO> transactions = new ArrayList<>();
        TransactionDTO transaction1 = new TransactionDTO(2L, "1", "3", "24.9", date);
        TransactionDTO transaction3 = new TransactionDTO(3L, "1", "2", "60", date);
        transactions.add(transaction1);
        transactions.add(transaction3);
        assertEquals(transactions, transactionService.findTransactionsByClientId(1));
    }

//    @Test(expected = LogicException.class)
//    public void createTransactionException() throws LogicException {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
//        String date = simpleDateFormat.format(new Date());
//        TransactionDTO transactionDTO = new TransactionDTO(0, 1, 2, 110, date);
//        assertNull(transactionService.save(transactionDTO, 1));
//    }
//
//    @Test
//    public void createTransaction() throws LogicException {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
//        String date = simpleDateFormat.format(new Date());
//        TransactionDTO transactionDTO = new TransactionDTO(0, 1, 2, 90, date);
//        TransactionDTO res = new TransactionDTO(1, 1, 2, 90, date);
//        assertEquals(res, transactionService.save(transactionDTO, 1));
//    }
//
//    @Test
//    public void findById() throws LogicException {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
//        String date = simpleDateFormat.format(new Date());
//        TransactionDTO transactionDTO = new TransactionDTO(0, 1, 3, 24.9, date);
//        transactionService.save(transactionDTO, 1);
//        TransactionDTO res = new TransactionDTO(1, 1, 3, 24.9, date);
//        assertEquals(res, transactionService.findById(1));
//    }
}