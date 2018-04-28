//package com.netcracker.komarov.services.impl;
//
//import com.netcracker.komarov.dao.entity.News;
//import com.netcracker.komarov.dao.entity.NewsStatus;
//import com.netcracker.komarov.dao.utils.DataBase;
//import com.netcracker.komarov.services.interfaces.ClientNewsService;
//import com.netcracker.komarov.services.interfaces.NewsService;
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Date;
//
//public class NewsServiceImplTest extends AbstractSpringTest {
//
//    @Autowired
//    private NewsService newsService;
//
//    @Autowired
//    private ClientNewsService clientNewsService;
//
//    @Before
//    public void init() {
//        DataBase.initTable();
//        DataBase.insertValues();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy  HH:mm");
//        News news1 = new News(0, simpleDateFormat.format(new Date()), "Hi", "Hello",
//                1, NewsStatus.GENERAL);
//        News news2 = new News(0, simpleDateFormat.format(new Date()), "NT", "Nice try",
//                1, NewsStatus.CLIENT);
//        News news3 = new News(0, simpleDateFormat.format(new Date()), "GG", "GOOD GAME",
//                2, NewsStatus.CLIENT);
//        News news4 = new News(0, simpleDateFormat.format(new Date()), "Banana", "Connector part of map" +
//                " on de_inferno", 1, NewsStatus.GENERAL);
//        news2 = newsService.addGeneralNews(news2, 1);
//        news3 = newsService.addGeneralNews(news3, 2);
//        newsService.addGeneralNews(news1, 1);
//        newsService.addGeneralNews(news4, 3);
//        Collection<Long> clientIds1 = new ArrayList<>();
//        clientIds1.add(0L);
//        clientNewsService.addClientNews(clientIds1, news2.getId());
//        Collection<Long> clientIds2 = new ArrayList<>();
//        clientIds2.add(1L);
//        clientIds2.add(3L);
//        clientNewsService.addClientNews(clientIds2, news3.getId());
//    }
//
//    @After
//    public void destroy() {
//        DataBase.dropTable();
//    }
//
//    @Test
//    public void getAllNews() {
//        Collection<News> newsCollection = new ArrayList<>();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy  HH:mm");
//        News news1 = new News(3, simpleDateFormat.format(new Date()), "Hi", "Hello",
//                1, NewsStatus.GENERAL);
//        News news2 = new News(1, simpleDateFormat.format(new Date()), "NT", "Nice try",
//                1, NewsStatus.CLIENT);
//        News news3 = new News(2, simpleDateFormat.format(new Date()), "GG", "GOOD GAME",
//                2, NewsStatus.CLIENT);
//        News news4 = new News(4, simpleDateFormat.format(new Date()), "Banana", "Connector part of map" +
//                " on de_inferno", 3, NewsStatus.GENERAL);
//        newsCollection.add(news2);
//        newsCollection.add(news3);
//        newsCollection.add(news1);
//        newsCollection.add(news4);
//        Assert.assertEquals(newsCollection, newsService.getAllNews());
//    }
//
//    @Test
//    public void getAllPersonalNews() {
//        Collection<News> newsCollection = new ArrayList<>();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy  HH:mm");
//        News news2 = new News(1, simpleDateFormat.format(new Date()), "NT", "Nice try",
//                1, NewsStatus.CLIENT);
//        News news3 = new News(2, simpleDateFormat.format(new Date()), "GG", "GOOD GAME",
//                2, NewsStatus.CLIENT);
//        newsCollection.add(news2);
//        newsCollection.add(news3);
//        Assert.assertEquals(newsCollection, newsService.getAllPersonalNews(1));
//    }
//
//    @Test
//    public void getPersonalNews() {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy  HH:mm");
//        News news = new News(2, simpleDateFormat.format(new Date()), "GG", "GOOD GAME",
//                2, NewsStatus.CLIENT);
//        Assert.assertEquals(news, newsService.getPersonalNews(2));
//    }
//
//    @Test
//    public void addGeneralNews() {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy  HH:mm");
//        News news = new News(0, simpleDateFormat.format(new Date()), "AWP", "Arctic" +
//                "warfare police",
//                0, NewsStatus.GENERAL);
//        News resNews = new News(5, simpleDateFormat.format(new Date()), "AWP", "Arctic" +
//                "warfare police",
//                2, NewsStatus.GENERAL);
//        Assert.assertEquals(resNews, newsService.addGeneralNews(news, 2));
//    }
//
//    @Test
//    public void getAllGeneralNews() {
//        Collection<News> newsCollection = new ArrayList<>();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy  HH:mm");
//        News news1 = new News(3, simpleDateFormat.format(new Date()), "Hi", "Hello",
//                1, NewsStatus.GENERAL);
//        News news4 = new News(4, simpleDateFormat.format(new Date()), "Banana", "Connector part of map" +
//                " on de_inferno", 3, NewsStatus.GENERAL);
//        newsCollection.add(news1);
//        newsCollection.add(news4);
//        Assert.assertEquals(newsCollection, newsService.getAllGeneralNews());
//    }
//
//    @Test
//    public void getAllNewsByStatus() {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy  HH:mm");
//        Collection<News> newsCollection1 = new ArrayList<>();
//        News news1 = new News(3, simpleDateFormat.format(new Date()), "Hi", "Hello",
//                1, NewsStatus.GENERAL);
//        News news4 = new News(4, simpleDateFormat.format(new Date()), "Banana", "Connector part of map" +
//                " on de_inferno", 3, NewsStatus.GENERAL);
//        newsCollection1.add(news1);
//        newsCollection1.add(news4);
//        Assert.assertEquals("Return GENERAL news", newsCollection1,
//                newsService.getAllNewsByStatus(NewsStatus.GENERAL));
//
//        Collection<News> newsCollection2 = new ArrayList<>();
//        News news2 = new News(1, simpleDateFormat.format(new Date()), "NT", "Nice try",
//                1, NewsStatus.CLIENT);
//        News news3 = new News(2, simpleDateFormat.format(new Date()), "GG", "GOOD GAME",
//                2, NewsStatus.CLIENT);
//        newsCollection2.add(news2);
//        newsCollection2.add(news3);
//        Assert.assertEquals("Return CLIENT news", newsCollection2,
//                newsService.getAllNewsByStatus(NewsStatus.CLIENT));
//    }
//}