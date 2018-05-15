package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Admin;
import com.netcracker.komarov.dao.entity.Client;
import com.netcracker.komarov.dao.entity.News;
import com.netcracker.komarov.dao.entity.NewsStatus;
import com.netcracker.komarov.dao.repository.AdminRepository;
import com.netcracker.komarov.dao.repository.ClientRepository;
import com.netcracker.komarov.dao.repository.NewsRepository;
import com.netcracker.komarov.services.dto.converter.NewsConverter;
import com.netcracker.komarov.services.dto.entity.NewsDTO;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.interfaces.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
    public NewsServiceImpl(NewsRepository newsRepository, ClientRepository clientRepository,
                           AdminRepository adminRepository, NewsConverter newsConverter) {
        this.newsRepository = newsRepository;
        this.clientRepository = clientRepository;
        this.adminRepository = adminRepository;
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
    public Collection<NewsDTO> getAllClientNewsById(long clientId) throws NotFoundException {
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
            String error = "There is no such client in database";
            logger.error(error);
            throw new NotFoundException(error);
        }
        return convertCollection(resultCollection);
    }

    @Transactional
    @Override
    public NewsDTO findById(long newsId) throws NotFoundException {
        Optional<News> optional = newsRepository.findById(newsId);
        News news;
        if (optional.isPresent()) {
            news = optional.get();
            logger.info("Return client news by ID");
        } else {
            String error = "There is no such news in database";
            logger.error(error);
            throw new NotFoundException(error);
        }
        return newsConverter.convertToDTO(news);
    }

    @Transactional
    @Override
    public NewsDTO addNews(NewsDTO newsDTO, long adminId) throws NotFoundException {
        News news = newsConverter.convertToEntity(newsDTO);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        news.setDate(simpleDateFormat.format(new Date()));
        Optional<Admin> optionalAdmin = adminRepository.findById(adminId);
        News temp = null;
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            news.setAdmin(admin);
            admin.getNews().add(news);
            logger.info("Addition new general new to database");
            temp = newsRepository.save(news);
        } else {
            String error = "There is no such admin in database";
            logger.error(error);
            throw new NotFoundException(error);
        }
        return newsConverter.convertToDTO(temp);
    }

    @Transactional
    @Override
    public Collection<NewsDTO> getAllNewsByStatus(NewsStatus newsStatus) {
        logger.info("Return all news by status");
        return convertCollection(newsRepository.findNewsByNewsStatus(newsStatus));
    }

    @Transactional
    @Override
    public NewsDTO addClientNews(Collection<Long> clientIds, long newsId) throws NotFoundException {
        Collection<Client> clients = clientRepository.findAll();
        Optional<News> optionalNews = newsRepository.findById(newsId);
        News news;
        News temp;
        if (optionalNews.isPresent()) {
            news = optionalNews.get();
            if (clientIds.size() == 0) {
                for (Client client : clients) {
                    news.getClients().add(client);
                    client.getNewsSet().add(news);
                }
            } else {
                for (long clientId : clientIds) {
                    Optional<Client> optionalClient = clientRepository.findById(clientId);
                    if (optionalClient.isPresent()) {
                        Client client = optionalClient.get();
                        news.getClients().add(client);
                        client.getNewsSet().add(news);
                    } else {
                        String error = "Client with ID " + clientId + " absent in database";
                        logger.error(error);
                        throw new NotFoundException(error);
                    }
                }
            }
            temp = newsRepository.save(news);
            logger.info("Send news to client");
        } else {
            String error = "There is no such news in database";
            logger.error(error);
            throw new NotFoundException(error);
        }
        return newsConverter.convertToDTO(temp);
    }

    @Transactional
    @Override
    public NewsDTO update(NewsDTO newsDTO) throws NotFoundException {
        News newNews = newsConverter.convertToEntity(newsDTO);
        Optional<News> optionalNews = newsRepository.findById(newsDTO.getId());
        News resNews;
        if (optionalNews.isPresent()) {
            News oldNews = optionalNews.get();
            oldNews.setTitle(newNews.getTitle());
            oldNews.setText(newNews.getText());
            resNews = newsRepository.saveAndFlush(oldNews);
            logger.info("News was edited by admin");
        } else {
            String error = "There is no such news in database";
            logger.error(error);
            throw new NotFoundException(error);
        }
        return newsConverter.convertToDTO(resNews);
    }
}
