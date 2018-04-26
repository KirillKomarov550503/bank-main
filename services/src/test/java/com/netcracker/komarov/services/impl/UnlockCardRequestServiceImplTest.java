package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.UnlockCardRequest;
import com.netcracker.komarov.dao.utils.DataBase;
import com.netcracker.komarov.services.interfaces.CardService;
import com.netcracker.komarov.services.interfaces.UnlockCardRequestService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;

public class UnlockCardRequestServiceImplTest extends AbstractSpringTest {

    @Autowired
    private UnlockCardRequestService cardRequestService;

    @Autowired
    private CardService cardService;

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
    public void getAllCardRequest() {
        cardService.lockCard(1);
        cardService.lockCard(3);
        cardRequestService.unlockCardRequest(1);
        cardRequestService.unlockCardRequest(3);
        Collection<UnlockCardRequest> requests = new ArrayList<>();
        UnlockCardRequest cardRequest1 = new UnlockCardRequest(1, 1);
        UnlockCardRequest cardRequest2 = new UnlockCardRequest(2, 3);
        requests.add(cardRequest1);
        requests.add(cardRequest2);
        Assert.assertEquals(requests, cardRequestService.getAllCardRequest());
    }

    @Test
    public void unlockCardRequest() {
        cardService.lockCard(2);
        UnlockCardRequest cardRequest = new UnlockCardRequest(1, 2);
        Assert.assertEquals(cardRequest, cardRequestService.unlockCardRequest(2));
    }
}