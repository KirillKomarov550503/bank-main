package com.netcracker.komarov.controllers.controller;

import com.google.gson.Gson;
import com.netcracker.komarov.dao.entity.NewsStatus;
import com.netcracker.komarov.services.dto.entity.NewsDTO;
import com.netcracker.komarov.services.interfaces.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("bank/v1")
public class NewsController {
    private NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @RequestMapping(value = "/admins/{adminId}/news", method = RequestMethod.POST)
    public ResponseEntity add(@PathVariable long adminId, @RequestBody NewsDTO newsDTO) {
        Gson gson = new Gson();
        NewsDTO dto = newsService.addNews(newsDTO, adminId);
        ResponseEntity responseEntity;
        if (dto == null) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(gson.toJson("Server error"));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.CREATED)
                    .body(gson.toJson(dto));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/client/{clientId}/news", method = RequestMethod.GET)
    public ResponseEntity getAllClientNewsById(@PathVariable long clientId) {
        Gson gson = new Gson();
        Collection<NewsDTO> dtos = newsService.getAllClientNewsById(clientId);
        ResponseEntity responseEntity;
        if (dtos == null) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(gson.toJson("Server error"));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(gson.toJson(dtos));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/news/{newsId}", method = RequestMethod.GET)
    public ResponseEntity getById(@PathVariable long newsId) {
        Gson gson = new Gson();
        NewsDTO dto = newsService.getNewsById(newsId);
        ResponseEntity responseEntity;
        if (dto == null) {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(gson.toJson("Not found news with such ID: " + newsId));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(gson.toJson(dto));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/admins/news", method = RequestMethod.GET)
    public ResponseEntity getCollection(@PathVariable long adminId, @RequestParam(name = "filter",
            required = false, defaultValue = "false") boolean filter, @RequestParam(name = "client",
            required = false, defaultValue = "false") boolean client) {
        Collection<NewsDTO> dtos;
        Gson gson = new Gson();
        ResponseEntity responseEntity;
        if (filter) {
            if (client) {
                dtos = newsService.getAllNewsByStatus(NewsStatus.CLIENT);
            } else {
                dtos = newsService.getAllNewsByStatus(NewsStatus.GENERAL);
            }
        } else {
            dtos = newsService.getAllNews();
        }
        if (dtos == null) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(gson.toJson("Server error"));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(gson.toJson(dtos.isEmpty() ? "Empty list of news" : dtos));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/admins/news/{newsId}", method = RequestMethod.POST)
    public ResponseEntity sendNewsToClients(@PathVariable long newsId,
                                            @RequestBody Collection<Long> clientIds) {
        Gson gson = new Gson();
        NewsDTO newsDTO = newsService.addClientNews(clientIds, newsId);
        ResponseEntity responseEntity;
        if (newsDTO == null) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(gson.toJson("Server error"));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.CREATED)
                    .body(gson.toJson(newsDTO));
        }
        return responseEntity;
    }
}
