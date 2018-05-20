package com.netcracker.komarov.controllers.controller;

import com.netcracker.komarov.dao.entity.NewsStatus;
import com.netcracker.komarov.services.dto.entity.NewsDTO;
import com.netcracker.komarov.services.exception.LogicException;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.interfaces.NewsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/bank/v1")
public class NewsController {
    private NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @ApiOperation(value = "Creation of new news")
    @RequestMapping(value = "/admins/{adminId}/news", method = RequestMethod.POST)
    public ResponseEntity add(@PathVariable long adminId, @RequestBody NewsDTO newsDTO) {
        ResponseEntity responseEntity;
        try {
            NewsDTO dto = newsService.addNews(newsDTO, adminId);
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting all client news by client ID")
    @RequestMapping(value = "/client/{clientId}/news", method = RequestMethod.GET)
    public ResponseEntity getAllClientNewsById(@PathVariable long clientId) {
        ResponseEntity responseEntity;
        try {
            Collection<NewsDTO> dtos = newsService.getAllClientNewsById(clientId);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(dtos);
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting all general news")
    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public ResponseEntity findAllGeneralNews() {
        Collection<NewsDTO> dtos = newsService.getAllNewsByStatus(NewsStatus.GENERAL);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @ApiOperation(value = "Selecting news by ID")
    @RequestMapping(value = "/news/{newsId}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable long newsId) {
        ResponseEntity responseEntity;
        try {
            NewsDTO dto = newsService.findById(newsId);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting all news by status")
    @RequestMapping(value = "/admins/news", method = RequestMethod.GET)
    public ResponseEntity getCollection(@RequestParam(name = "filter",
            required = false, defaultValue = "false") boolean filter, @RequestParam(name = "client",
            required = false, defaultValue = "false") boolean client) {
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
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @ApiOperation(value = "Sending news to clients")
    @RequestMapping(value = "/admins/news/{newsId}", method = RequestMethod.POST)
    public ResponseEntity sendNewsToClients(@PathVariable long newsId, @RequestBody Collection<Long> clientIds) {
        ResponseEntity responseEntity;
        try {
            NewsDTO dto = newsService.addClientNews(clientIds, newsId);
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        } catch (LogicException e) {
            responseEntity = getInternalServerErrorResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Remarking news")
    @RequestMapping(value = "/admins/news/{newsId}", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody NewsDTO requestNewsDTO, @PathVariable long newsId) {
        ResponseEntity responseEntity;
        try {
            requestNewsDTO.setId(newsId);
            NewsDTO dto = newsService.update(requestNewsDTO);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    private ResponseEntity getNotFoundResponseEntity(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    private ResponseEntity getInternalServerErrorResponseEntity(String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }
}
