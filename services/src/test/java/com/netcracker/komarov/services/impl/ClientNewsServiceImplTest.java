//package com.netcracker.komarov.services.impl;
//
//import com.netcracker.komarov.dao.entity.ClientNews;
//import com.netcracker.komarov.dao.utils.DataBase;
//import com.netcracker.komarov.services.interfaces.ClientNewsService;
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//public class ClientNewsServiceImplTest extends AbstractSpringTest {
//
//    @Autowired
//    private ClientNewsService clientNewsService;
//
//    @Before
//    public void init() {
//        DataBase.initTable();
//        DataBase.insertValues();
//    }
//
//    @After
//    public void destroy() {
//        DataBase.dropTable();
//    }
//
//    @Test
//    public void getAllClientNews() {
//        ClientNews clientNews1 = new ClientNews(1, 1, 1);
//        ClientNews clientNews2 = new ClientNews(2, 2, 1);
//        ClientNews clientNews3 = new ClientNews(3, 1, 2);
//        Collection<Long> clientIds1 = new ArrayList<>();
//        clientIds1.add(1L);
//        clientIds1.add(2L);
//        clientNewsService.addClientNews(clientIds1, 1);
//        Collection<Long> clientIds2 = new ArrayList<>();
//        clientIds2.add(1L);
//        clientNewsService.addClientNews(clientIds2, 2);
//        Collection<ClientNews> clientNewsCollection = new ArrayList<>();
//        clientNewsCollection.add(clientNews1);
//        clientNewsCollection.add(clientNews2);
//        clientNewsCollection.add(clientNews3);
//        Assert.assertEquals(clientNewsCollection, clientNewsService.getAllClientNews());
//    }
//
//    @Test
//    public void addClientNews() {
//        ClientNews clientNews1 = new ClientNews(1, 1, 1);
//        ClientNews clientNews2 = new ClientNews(2, 2, 1);
//        Collection<Long> clientIds1 = new ArrayList<>();
//        clientIds1.add(1L);
//        clientIds1.add(2L);
//        Collection<ClientNews> clientNewsCollection = new ArrayList<>();
//        clientNewsCollection.add(clientNews1);
//        clientNewsCollection.add(clientNews2);
//        Assert.assertEquals(clientNewsCollection, clientNewsService.addClientNews(clientIds1, 1));
//    }
//}