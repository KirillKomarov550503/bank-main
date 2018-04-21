package dev3.bank.impl;

import dev3.bank.dao.interfaces.ClientNewsDAO;
import dev3.bank.dao.interfaces.NewsDAO;
import dev3.bank.entity.News;
import dev3.bank.entity.NewsStatus;
import dev3.bank.factory.DAOFactory;
import dev3.bank.interfaces.NewsService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class NewsServiceImpl implements NewsService {
    private NewsDAO newsDAO;
    private ClientNewsDAO clientNewsDAO;
    private static NewsServiceImpl newsService;

    private NewsServiceImpl() {
    }

    public static synchronized NewsServiceImpl getNewsService() {
        if (newsService == null) {
            newsService = new NewsServiceImpl();
        }
        return newsService;
    }

    @Override
    public void setDAO(DAOFactory daoFactory) {
        newsDAO = daoFactory.getNewsDAO();
        clientNewsDAO = daoFactory.getClientNewsDAO();
    }

    @Override
    public Collection<News> getAllNews() {
        Collection<News> temp = null;
        try {
            temp = newsDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
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
                            e.printStackTrace();
                        }
                        return stream;
                    })
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newsCollection;
    }

    @Override
    public News getPersonalNews(long newsId) {
        News temp = null;
        try {
            temp = newsDAO.getById(newsId);
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    public Collection<News> getAllGeneralNews() {
        Collection<News> temp = null;
        try {
            temp = newsDAO.getNewsByStatus(NewsStatus.GENERAL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    public Collection<News> getAllNewsByStatus(NewsStatus newsStatus) {
        Collection<News> newsCollection = null;
        try {
            newsCollection = newsDAO.getNewsByStatus(newsStatus);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newsCollection;
    }
}
