package dev3.bank.impl;

import dev3.bank.dao.utils.DataBase;
import dev3.bank.entity.Account;
import dev3.bank.factory.DAOFactory;
import dev3.bank.factory.PostgreSQLDAOFactory;
import dev3.bank.interfaces.AccountService;
import dev3.bank.interfaces.UnlockAccountRequestService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class AccountServiceImplTest {
    private AccountService accountService;
    private UnlockAccountRequestService accountRequestService;

    @Before
    public void init() {
        DAOFactory daoFactory = PostgreSQLDAOFactory.getPostgreSQLDAOFactory();
        accountService = AccountServiceImpl.getAccountService();
        accountService.setDAO(daoFactory);
        accountRequestService = UnlockAccountRequestServiceImpl.getUnlockAccountRequestService();
        accountRequestService.setDAO(daoFactory);
        DataBase.dropTable();
        DataBase.initTable();
        DataBase.insertValues();
    }

    @Test
    public void lockAccount() {
        Account account = new Account(2, 0.0, true, 1, 100);
        Assert.assertEquals(account, accountService.lockAccount(2));
    }

    @Test
    public void getAllAccounts() {
        Account account1 = new Account(1, 0.0, false, 1, 423);
        Account account2 = new Account(2, 0.0, false, 1, 100);
        Account account3 = new Account(3, 0.0, false, 3, 123);
        Collection<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        Assert.assertEquals(accounts, accountService.getAllAccounts());
    }

    @Test
    public void unlockAccount() {
        accountService.lockAccount(1);
        accountRequestService.unlockAccountRequest(1);
        accountService.unlockAccount(1);
        Collection<Account> accounts = new ArrayList<>();
        Account account1 = new Account(1, 0.0, false, 1, 423);
        Account account2 = new Account(2, 0.0, false, 1, 100);
        Account account3 = new Account(3, 0.0, false, 3, 123);
        accounts.add(account2);
        accounts.add(account3);
        accounts.add(account1);
        Assert.assertEquals(accounts, accountService.getAllAccounts());
    }

    @Test
    public void getAllUnlockAccountRequest() {
        Collection<Account> accounts = new ArrayList<>();
        Assert.assertEquals("Must return empty list", accounts, accountService.getAllUnlockAccountRequest());
        accountService.lockAccount(1);
        accountService.lockAccount(3);
        accountRequestService.unlockAccountRequest(1);
        accountRequestService.unlockAccountRequest(3);
        Account account1 = new Account(1, 0.0, true, 1, 423);
        Account account3 = new Account(3, 0.0, true, 3, 123);
        accounts.add(account1);
        accounts.add(account3);
        Assert.assertEquals(accounts, accountService.getAllUnlockAccountRequest());
    }

    @Test
    public void refill() {
        Account account = new Account(2, 100.0, false, 1, 100);
        Assert.assertEquals(account, accountService.refill(2));
    }

    @Test
    public void getLockAccounts() {
        Collection<Account> accounts = new ArrayList<>();
        Assert.assertEquals("Must return empty list", accounts, accountService.getLockAccounts(1));
        accountService.lockAccount(1);
        accountService.lockAccount(2);
        Account account1 = new Account(1, 0.0, true, 1, 423);
        Account account2 = new Account(2, 0.0, true, 1, 100);
        accounts.add(account1);
        accounts.add(account2);
        Assert.assertEquals(accounts, accountService.getLockAccounts(1));
    }

    @Test
    public void getUnlockAccounts() {
        Collection<Account> accounts = new ArrayList<>();
        Assert.assertEquals("Must return empty list", accounts, accountService.getLockAccounts(1));
        accountService.lockAccount(1);
        Account account2 = new Account(2, 0.0, false, 1, 100);
        accounts.add(account2);
        Assert.assertEquals(accounts, accountService.getUnlockAccounts(1));
    }

    @Test
    public void createAccount() {
        Account res = new Account(4, 0.0, false, 2, 0001);
        Account account = new Account(0, 0.0, false, 0, 0001);
        Assert.assertEquals(res, accountService.createAccount(account, 2));
    }
}