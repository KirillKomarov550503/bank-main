package dev3.bank.impl;

import dev3.bank.dao.utils.DataBase;
import dev3.bank.entity.ClientNews;
import dev3.bank.factory.DAOFactory;
import dev3.bank.factory.PostgreSQLDAOFactory;
import dev3.bank.interfaces.ClientNewsService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class ClientNewsServiceImplTest {
    private ClientNewsService clientNewsService;

    @Before
    public void init() {
        DataBase.initTable();
        DataBase.insertValues();
        DAOFactory daoFactory = PostgreSQLDAOFactory.getPostgreSQLDAOFactory();
        clientNewsService = ClientNewsServiceImpl.getClientNewsService();
        clientNewsService.setDAO(daoFactory);
    }

    @After
    public void destroy() {
        DataBase.dropTable();
    }

    @Test
    public void getAllClientNews() {
        ClientNews clientNews1 = new ClientNews(1, 1, 1);
        ClientNews clientNews2 = new ClientNews(2, 2, 1);
        ClientNews clientNews3 = new ClientNews(3, 1, 2);
        Collection<Long> clientIds1 = new ArrayList<>();
        clientIds1.add(1L);
        clientIds1.add(2L);
        clientNewsService.addClientNews(clientIds1, 1);
        Collection<Long> clientIds2 = new ArrayList<>();
        clientIds2.add(1L);
        clientNewsService.addClientNews(clientIds2, 2);
        Collection<ClientNews> clientNewsCollection = new ArrayList<>();
        clientNewsCollection.add(clientNews1);
        clientNewsCollection.add(clientNews2);
        clientNewsCollection.add(clientNews3);
        Assert.assertEquals(clientNewsCollection, clientNewsService.getAllClientNews());
    }

    @Test
    public void addClientNews() {
        ClientNews clientNews1 = new ClientNews(1, 1, 1);
        ClientNews clientNews2 = new ClientNews(2, 2, 1);
        Collection<Long> clientIds1 = new ArrayList<>();
        clientIds1.add(1L);
        clientIds1.add(2L);
        Collection<ClientNews> clientNewsCollection = new ArrayList<>();
        clientNewsCollection.add(clientNews1);
        clientNewsCollection.add(clientNews2);
        Assert.assertEquals(clientNewsCollection, clientNewsService.addClientNews(clientIds1, 1));
    }
}