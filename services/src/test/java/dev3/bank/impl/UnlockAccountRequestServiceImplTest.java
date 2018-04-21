package dev3.bank.impl;

import dev3.bank.dao.utils.DataBase;
import dev3.bank.entity.UnlockAccountRequest;
import dev3.bank.interfaces.AccountService;
import dev3.bank.interfaces.UnlockAccountRequestService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;

public class UnlockAccountRequestServiceImplTest extends AbstractSpringTest {

    @Autowired
    private UnlockAccountRequestService accountRequestService;

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
    public void getAllAccountRequest() {
        accountService.lockAccount(2);
        accountService.lockAccount(3);
        accountRequestService.unlockAccountRequest(2);
        accountRequestService.unlockAccountRequest(3);
        Collection<UnlockAccountRequest> requests = new ArrayList<>();
        UnlockAccountRequest accountRequest1 = new UnlockAccountRequest(1, 2);
        UnlockAccountRequest accountRequest2 = new UnlockAccountRequest(2, 3);
        requests.add(accountRequest1);
        requests.add(accountRequest2);
        Assert.assertEquals(requests, accountRequestService.getAllAccountRequest());
    }

    @Test
    public void unlockAccountRequest() {
        accountService.lockAccount(1);
        UnlockAccountRequest unlockAccountRequest = new UnlockAccountRequest(1, 1);
        Assert.assertEquals(unlockAccountRequest, accountRequestService.unlockAccountRequest(1));
    }
}