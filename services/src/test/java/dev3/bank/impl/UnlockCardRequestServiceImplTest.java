package dev3.bank.impl;

import dev3.bank.dao.utils.DataBase;
import dev3.bank.entity.UnlockCardRequest;
import dev3.bank.factory.DAOFactory;
import dev3.bank.factory.PostgreSQLDAOFactory;
import dev3.bank.interfaces.CardService;
import dev3.bank.interfaces.UnlockCardRequestService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class UnlockCardRequestServiceImplTest {
    private UnlockCardRequestService cardRequestService;
    private CardService cardService;

    @Before
    public void init() {
        DataBase.initTable();
        DataBase.insertValues();
        DAOFactory daoFactory = PostgreSQLDAOFactory.getPostgreSQLDAOFactory();
        cardRequestService = UnlockCardRequestServiceImpl.getUnlockCardRequestService();
        cardRequestService.setDAO(daoFactory);
        cardService = CardServiceImpl.getCardService();
        cardService.setDAO(daoFactory);
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