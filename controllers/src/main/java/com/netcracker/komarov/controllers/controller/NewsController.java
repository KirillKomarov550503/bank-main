package com.netcracker.komarov.controllers.controller;

import com.netcracker.komarov.controllers.exception.ServerException;
import com.netcracker.komarov.dao.entity.NewsStatus;
import com.netcracker.komarov.services.dto.entity.NewsDTO;
import com.netcracker.komarov.services.interfaces.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("api/v1")
public class NewsController {
    private NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/admins/{adminId}/news", method = RequestMethod.POST)
    public NewsDTO add(@PathVariable long adminId, @RequestBody NewsDTO newsDTO) {
        NewsDTO dto = newsService.addNews(newsDTO, adminId);
        if (dto == null) {
            throw new ServerException("Server can't create new news");
        }
        return dto;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/client/{clientId}/news", method = RequestMethod.GET)
    public Collection<NewsDTO> getAllClientNewsById(@PathVariable long clientId) {
        Collection<NewsDTO> dtos = newsService.getAllClientNewsById(clientId);
        if (dtos == null) {
            throw new NullPointerException("client");
        }
        return dtos;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/news/{newsId}", method = RequestMethod.GET)
    public NewsDTO getById(@PathVariable long newsId) {
        NewsDTO dto = newsService.getNewsById(newsId);
        if (dto == null) {
            throw new NullPointerException("news");
        }
        return dto;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/admins/{admiId}/news", method = RequestMethod.GET)
    public Collection<NewsDTO> getCollection(@PathVariable long adminId, @RequestParam(name = "filter", required = false,
            defaultValue = "false") boolean filter, @RequestParam(name = "client", required = false) boolean client) {
        Collection<NewsDTO> dtos;
        if (filter) {
            if (client) {
                dtos = newsService.getAllNewsByStatus(NewsStatus.CLIENT);
            } else {
                dtos = newsService.getAllNewsByStatus(NewsStatus.GENERAL);
            }
        } else {
            dtos = newsService.getAllNews();
        }
        return dtos;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/admins/{adminId}/news/{newsId}", method = RequestMethod.POST)
    public NewsDTO addClientNews(@PathVariable long newsId, @PathVariable long adminId,
                                 @RequestBody Collection<Long> clientIds) {
        NewsDTO newsDTO = newsService.addClientNews(clientIds, newsId);
        if (newsDTO == null) {
            throw new NullPointerException("news");
        }
        return newsDTO;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NullPointerException.class)
    public String handleNullPointerException(NullPointerException ex) {
        return "Not found this " + ex.getMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = ServerException.class)
    public String handleServerException(ServerException ex) {
        return ex.getMessage();
    }
}
