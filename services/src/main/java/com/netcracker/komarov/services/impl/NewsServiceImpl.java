package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.*;
import com.netcracker.komarov.dao.repository.AdminRepository;
import com.netcracker.komarov.dao.repository.ClientNewsRepository;
import com.netcracker.komarov.dao.repository.ClientRepository;
import com.netcracker.komarov.dao.repository.NewsRepository;
import com.netcracker.komarov.services.dto.converter.NewsConverter;
import com.netcracker.komarov.services.dto.entity.NewsDTO;
import com.netcracker.komarov.services.exception.LogicException;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.interfaces.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {
    private NewsRepository newsRepository;
    private ClientRepository clientRepository;
    private AdminRepository adminRepository;
    private NewsConverter newsConverter;
    private ClientNewsRepository clientNewsRepository;
    private Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository, ClientRepository clientRepository,
                           AdminRepository adminRepository, NewsConverter newsConverter,
                           ClientNewsRepository clientNewsRepository) {
        this.newsRepository = newsRepository;
        this.clientRepository = clientRepository;
        this.adminRepository = adminRepository;
        this.newsConverter = newsConverter;
        this.clientNewsRepository = clientNewsRepository;
    }

    private boolean isAbsentInDatabase(long clientId, long newsId) {
        ClientNews clientNews = clientNewsRepository.findClientNewsByClientIdAndAndNewsId(clientId, newsId);
        return clientNews == null;
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
        List<News> resultCollection;
        if (optionalClient.isPresent()) {
            System.err.println("Collection: " + clientNewsRepository.findAll());
            resultCollection = clientNewsRepository.findAll()
                    .stream()
                    .filter(clientNews -> clientNews.getClientId() == clientId
                            || clientNews.getClientId() == 0L)
                    .map(clientNews -> newsRepository.findById(clientNews.getNewsId()).get())
                    .collect(Collectors.toList());
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
        News temp;
        if (optionalAdmin.isPresent()) {
            news.setAdminId(adminId);
            temp = newsRepository.save(news);
            logger.info("Add new news to database");
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
    public NewsDTO addClientNews(Collection<Long> clientIds, long newsId) throws NotFoundException, LogicException {
        Optional<News> optionalNews = newsRepository.findById(newsId);
        News news;
        for (Long clientId : clientIds) {
            if (!clientRepository.findById(clientId).isPresent()) {
                String error = "Client with ID " + clientId + " absent in database";
                logger.error(error);
                throw new NotFoundException(error);
            }
        }
        if (optionalNews.isPresent()) {
            news = optionalNews.get();
            if (news.getNewsStatus().equals(NewsStatus.GENERAL)) {
                String error = "Try to send general news to clients";
                logger.error(error);
                throw new LogicException(error);
            }
            if (clientIds.isEmpty()) {
                if (isAbsentInDatabase(0L, newsId)) {
                    clientNewsRepository.save(new ClientNews(0L, newsId));
                }
            } else {
                clientIds.stream()
                        .filter(clientId -> isAbsentInDatabase(clientId, newsId))
                        .forEach(clientId -> clientNewsRepository.save(new ClientNews(clientId, newsId)));
            }
        } else {
            String error = "There is no such news in database";
            logger.error(error);
            throw new NotFoundException(error);
        }
        return newsConverter.convertToDTO(news);
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
