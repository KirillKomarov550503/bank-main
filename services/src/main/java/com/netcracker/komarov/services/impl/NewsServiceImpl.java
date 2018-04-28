package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Client;
import com.netcracker.komarov.dao.entity.News;
import com.netcracker.komarov.dao.entity.NewsStatus;
import com.netcracker.komarov.dao.factory.RepositoryFactory;
import com.netcracker.komarov.dao.repository.ClientRepository;
import com.netcracker.komarov.dao.repository.NewsRepository;
import com.netcracker.komarov.services.interfaces.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {
    private NewsRepository newsRepository;
    private ClientRepository clientRepository;
    private Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);

    @Autowired
    public NewsServiceImpl(RepositoryFactory repositoryFactory, ClientRepository clientRepository) {
        this.newsRepository = repositoryFactory.getNewsRepository();
        this.clientRepository = clientRepository;
    }

    @Transactional
    @Override
    public Collection<News> getAllNews() {
        logger.info("Return all news");
        return newsRepository.findAll();
    }

    @Transactional
    @Override
    public Collection<News> getAllClientNewsById(long clientId) {
        Collection<News> clientsNews = newsRepository.findAll()
                .stream()
                .filter(news -> news.getNewsStatus().equals(NewsStatus.CLIENT))
                .collect(Collectors.toList());
        Collection<News> resultCollection = new ArrayList<>();
        for (News news : clientsNews) {
            Collection<Client> clients = news.getClients();
            for (Client client : clients) {
                if (client.getId() == clientId) {
                    resultCollection.add(news);
                }
            }
        }
        logger.info("Return all client news By client ID");
        return resultCollection;
    }

    @Transactional
    @Override
    public News getPersonalNews(long newsId) {
        logger.info("Return client news by ID");
        return newsRepository.findById(newsId).get();
    }

    @Transactional
    @Override
    public News addGeneralNews(News news, long adminId) {
        news.getAdmin().setId(adminId);
        logger.info("Addition new general new to database");
        return newsRepository.save(news);
    }

    @Transactional
    @Override
    public Collection<News> getAllGeneralNews() {
        logger.info("Return all general news");
        return newsRepository.findNewsByNewsStatus(NewsStatus.GENERAL);
    }

    @Transactional
    @Override
    public Collection<News> getAllNewsByStatus(NewsStatus newsStatus) {
        logger.info("Return all news by status");
        return newsRepository.findNewsByNewsStatus(newsStatus);
    }

    @Override
    public News addClientNews(Collection<Long> clientIds, long newsId) {
        Collection<Client> clients = clientRepository.findAll();
        News news = newsRepository.findById(newsId).get();
        if (clientIds.size() == 0) {
            for (Client client : clients) {
                news.getClients().add(client);
                client.getNewsSet().add(news);
            }
        } else {
            for (long clientId : clientIds) {
                Client client = clientRepository.findById(clientId).get();
                news.getClients().add(client);
                client.getNewsSet().add(news);
            }
        }
        return news;
    }
}
