package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Account;
import com.netcracker.komarov.dao.entity.RequestStatus;
import com.netcracker.komarov.dao.utils.DataBase;
import com.netcracker.komarov.services.interfaces.AccountService;
import com.netcracker.komarov.services.interfaces.RequestService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;


public class AccountServiceImplTest extends AbstractSpringTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RequestService requestService;

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
    public void lockAccount() {
        Account account = new Account(2, 0.0, true, 1);
        Assert.assertEquals(account, accountService.lockAccount(2));
    }

    @Test
    public void getAllAccounts() {
        Account account1 = new Account(1, 0.0, false, 1);
        Account account2 = new Account(2, 0.0, false, 1);
        Account account3 = new Account(3, 0.0, false, 3);
        Collection<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        Assert.assertEquals(accounts, accountService.getAllAccounts());
    }

    @Test
    public void unlockAccount() {
        accountService.lockAccount(1);
        requestService.saveRequest(1, RequestStatus.ACCOUNT);
        Account account = new Account(1, 0, false, 1);
        Assert.assertEquals(account, accountService.unlockAccount(1));
    }

    @Test
    public void getAllAccountRequest() {
        Collection<Account> accounts = new ArrayList<>();
        Assert.assertEquals("Must return empty list", accounts, accountService.getAllAccountRequest());
        accountService.lockAccount(1);
        accountService.lockAccount(3);
        requestService.saveRequest(1, RequestStatus.ACCOUNT);
        requestService.saveRequest(3, RequestStatus.ACCOUNT);
        Account account1 = new Account(1, 0.0, true, 1);
        Account account3 = new Account(3, 0.0, true, 3);
        accounts.add(account1);
        accounts.add(account3);
        Assert.assertEquals(accounts, accountService.getAllAccountRequest());
    }

    @Test
    public void refill() {
        Account account = new Account(2, 100.0, false, 1);
        Assert.assertEquals(account, accountService.refill(2));
    }

    @Test
    public void getLockAccounts() {
        Collection<Account> accounts = new ArrayList<>();
        Assert.assertEquals("Must return empty list", accounts, accountService.getLockAccounts(1));
        accountService.lockAccount(1);
        accountService.lockAccount(2);
        Account account1 = new Account(1, 0.0, true, 1);
        Account account2 = new Account(2, 0.0, true, 1);
        accounts.add(account1);
        accounts.add(account2);
        Assert.assertEquals(accounts, accountService.getLockAccounts(1));
    }

    @Test
    public void getUnlockAccounts() {
        Collection<Account> accounts = new ArrayList<>();
        Assert.assertEquals("Must return empty list", accounts, accountService.getLockAccounts(1));
        accountService.lockAccount(1);
        Account account2 = new Account(2, 0.0, false, 1);
        accounts.add(account2);
        Assert.assertEquals(accounts, accountService.getUnlockAccounts(1));
    }

    @Test
    public void createAccount() {
        Account res = new Account(4, 0.0, false, 2);
        Account account = new Account(0, 0.0, false, 0);
        Assert.assertEquals(res, accountService.createAccount(account, 2));
    }
}