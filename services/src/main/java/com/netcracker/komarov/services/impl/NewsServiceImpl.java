package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.ClientNews;
import com.netcracker.komarov.dao.entity.News;
import com.netcracker.komarov.dao.entity.NewsStatus;
import com.netcracker.komarov.dao.interfaces.ClientDAO;
import com.netcracker.komarov.dao.interfaces.ClientNewsDAO;
import com.netcracker.komarov.dao.interfaces.NewsDAO;
import com.netcracker.komarov.dao.utils.DataBase;
import com.netcracker.komarov.services.factory.DAOFactory;
import com.netcracker.komarov.services.interfaces.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class NewsServiceImpl implements NewsService {
    private NewsDAO newsDAO;
    private ClientNewsDAO clientNewsDAO;
    private ClientDAO clientDAO;

    @Autowired
    public NewsServiceImpl(DAOFactory daoFactory) {
        this.newsDAO = daoFactory.getNewsDAO();
        this.clientNewsDAO = daoFactory.getClientNewsDAO();
        this.clientDAO = daoFactory.getClientDAO();
    }

    @Override
    public void addClientNews(Collection<Long> clientIds, long newsId) {
        Connection connection = DataBase.getConnection();
        try {
            connection.setAutoCommit(false);
            for (long clientId : clientIds) {
                ClientNews clientNews = new ClientNews();
                clientNews.setClientId(clientId);
                clientNews.setNewsId(newsId);
                clientNewsDAO.add(clientNews);
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.out.println("SQL exception");
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("SQL exception");
            }
        }
    }

    @Override
    public Collection<News> getAllNews() {
        Collection<News> temp = null;
        try {
            temp = newsDAO.getAll();
        } catch (SQLException e) {
            System.out.println("SQL exception");
        }
        return temp;
    }

    @Override
    public Collection<News> getAllPersonalNews(long clientId) {
        Collection<News> newsCollection = null;
        try {
            newsCollection = clientNewsDAO.getAllByClientId(clientId)
                    .stream()
                    .flatMap(clientNews -> {
                        Stream<News> stream = null;
                        try {
                            stream = Stream.of(newsDAO.getById(clientNews.getNewsId()));
                        } catch (SQLException e) {
                            System.out.println("SQL exception");
                        }
                        return stream;
                    })
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            System.out.println("SQL exception");
        }
        return newsCollection;
    }

    @Override
    public News findClientNewsById(long newsId) {
        News temp = null;
        try {
            temp = newsDAO.getById(newsId);
        } catch (SQLException e) {
            System.out.println("SQL exception");
        }
        return temp;
    }

    @Override
    public News addGeneralNews(News news, long adminId) {
        News temp = null;
        try {
            news.setAdminId(adminId);
            temp = newsDAO.add(news);
        } catch (SQLException e) {
            System.out.println("SQL exception");
        }
        return temp;
    }


    @Override
    public Collection<News> getAllNewsByStatus(NewsStatus newsStatus) {
        Collection<News> newsCollection = null;
        try {
            newsCollection = newsDAO.getNewsByStatus(newsStatus);
        } catch (SQLException e) {
            System.out.println("SQL exception");
        }
        return newsCollection;
    }
}
