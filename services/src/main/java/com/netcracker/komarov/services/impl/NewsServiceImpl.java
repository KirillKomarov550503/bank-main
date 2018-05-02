package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Admin;
import com.netcracker.komarov.dao.entity.Client;
import com.netcracker.komarov.dao.entity.News;
import com.netcracker.komarov.dao.entity.NewsStatus;
import com.netcracker.komarov.dao.factory.RepositoryFactory;
import com.netcracker.komarov.dao.repository.AdminRepository;
import com.netcracker.komarov.dao.repository.ClientRepository;
import com.netcracker.komarov.dao.repository.NewsRepository;
import com.netcracker.komarov.services.dto.converter.NewsConverter;
import com.netcracker.komarov.services.dto.entity.NewsDTO;
import com.netcracker.komarov.services.interfaces.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {
    private NewsRepository newsRepository;
    private ClientRepository clientRepository;
    private AdminRepository adminRepository;
    private NewsConverter newsConverter;
    private Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);

    @Autowired
    public NewsServiceImpl(RepositoryFactory repositoryFactory, NewsConverter newsConverter) {
        this.newsRepository = repositoryFactory.getNewsRepository();
        this.clientRepository = repositoryFactory.getClientRepository();
        this.adminRepository = repositoryFactory.getAdminRepository();
        this.newsConverter = newsConverter;
    }

    private Collection<NewsDTO> convertCollection(Collection<News> newsCollection) {
        return newsCollection.stream()
                .map(news -> newsConverter.convertToDTO(news))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Collection<NewsDTO> getAllNews() {
        logger.info("Return all news");
        return convertCollection(newsRepository.findAll());
    }

    @Transactional
    @Override
    public Collection<NewsDTO> getAllClientNewsById(long clientId) {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Collection<News> resultCollection = new ArrayList<>();
        if (optionalClient.isPresent()) {
            Collection<News> clientsNews = newsRepository.findAll()
                    .stream()
                    .filter(news -> news.getNewsStatus().equals(NewsStatus.CLIENT))
                    .collect(Collectors.toList());
            for (News news : clientsNews) {
                Collection<Client> clients = news.getClients();
                for (Client client : clients) {
                    if (client.getId() == clientId) {
                        resultCollection.add(news);
                    }
                }
            }
            logger.info("Return all client news By client ID");
        } else {
            logger.info("There is no such client in database");
        }
        return convertCollection(resultCollection);
    }

    @Transactional
    @Override
    public NewsDTO getNewsById(long newsId) {
        Optional<News> optional = newsRepository.findById(newsId);
        News news = null;
        if (optional.isPresent()) {
            news = optional.get();
            logger.info("Return client news by ID");
        } else {
            logger.info("There is no such news in database");
        }
        return newsConverter.convertToDTO(news);
    }

    @Transactional
    @Override
    public NewsDTO addNews(NewsDTO newsDTO, long adminId) {
        News news = newsConverter.convertToEntity(newsDTO);
        Optional<Admin> optionalAdmin = adminRepository.findById(adminId);
        News temp = null;
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            news.setAdmin(admin);
            admin.getNews().add(news);
            logger.info("Addition new general new to database");
            temp = newsRepository.save(news);
        } else {
            logger.info("There is no such admin in database");
        }
        return newsConverter.convertToDTO(temp);
    }

    @Transactional
    @Override
    public Collection<NewsDTO> getAllGeneralNews() {
        logger.info("Return all general news");
        return convertCollection(newsRepository.findNewsByNewsStatus(NewsStatus.GENERAL));
    }

    @Transactional
    @Override
    public Collection<NewsDTO> getAllNewsByStatus(NewsStatus newsStatus) {
        logger.info("Return all news by status");
        return convertCollection(newsRepository.findNewsByNewsStatus(newsStatus));
    }

    @Transactional
    @Override
    public NewsDTO addClientNews(Collection<Long> clientIds, long newsId) {
        Collection<Client> clients = clientRepository.findAll();
        Optional<News> optionalNews = newsRepository.findById(newsId);
        News news;
        News temp = null;
        if (optionalNews.isPresent()) {
            news = optionalNews.get();
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
            temp = newsRepository.save(news);
            logger.info("Send news to client");
        } else {
            logger.info("There is no such news in database");
        }
        return newsConverter.convertToDTO(temp);
    }
}