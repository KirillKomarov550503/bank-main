package dev3.bank.impl;

import dev3.bank.dao.utils.DataBase;
import dev3.bank.entity.Account;
import dev3.bank.factory.DAOFactory;
import dev3.bank.factory.PostgreSQLDAOFactory;
import dev3.bank.interfaces.AccountService;
import dev3.bank.interfaces.ClientService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AccountServiceImplTest {
    private AccountService accountService;
    private List<Account> accounts;

    @Before
    public void init() {
        DAOFactory daoFactory = PostgreSQLDAOFactory.getPostgreSQLDAOFactory();
        accountService = AccountServiceImpl.getAccountService();
        accountService.setDAO(daoFactory);
        DataBase.dropTable();
        DataBase.initTable();
        DataBase.insertValues();
        accounts = new ArrayList<>();

        Account account1 = new Account();
        account1.setId(1);
        account1.setLocked(false);
        account1.setBalance(0.0);
        account1.setAccountId(423);
        account1.setClientId(1);
        accounts.add(account1);

        Account account2 = new Account();
        account2.setId(2);
        account2.setClientId(1);
        account2.setBalance(0.0);
        account2.setLocked(false);
        account2.setAccountId(100);
        accounts.add(account2);
        Assert.assertEquals(account2, accountService.lockAccount(2));
    }

    @Test
    public void lockAccount() {
        Account account = new Account();
        account.setId(2);
        account.setClientId(1);
        account.setBalance(0.0);
        account.setLocked(true);
        account.setAccountId(100);
        Assert.assertEquals(account, accountService.lockAccount(2));
    }

    @Test
    public void getAllAccounts() {
    }

    @Test
    public void unlockAccount() {
    }

    @Test
    public void getAllUnlockAccountRequest() {
    }

    @Test
    public void refill() {
    }

    @Test
    public void getLockAccounts() {
    }

    @Test
    public void getUnlockAccounts() {
    }

    @Test
    public void createAccount() {
    }
}