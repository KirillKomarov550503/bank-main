package com.netcracker.komarov.controllers.controller;

import com.google.gson.Gson;
import com.netcracker.komarov.dao.entity.NewsStatus;
import com.netcracker.komarov.services.dto.entity.AdminDTO;
import com.netcracker.komarov.services.dto.entity.ClientDTO;
import com.netcracker.komarov.services.dto.entity.NewsDTO;
import com.netcracker.komarov.services.interfaces.AdminService;
import com.netcracker.komarov.services.interfaces.ClientService;
import com.netcracker.komarov.services.interfaces.NewsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("bank/v1")
public class NewsController {
    private NewsService newsService;
    private AdminService adminService;
    private ClientService clientService;
    private Gson gson;

    @Autowired
    public NewsController(NewsService newsService, AdminService adminService,
                          ClientService clientService, Gson gson) {
        this.newsService = newsService;
        this.adminService = adminService;
        this.clientService = clientService;
        this.gson = gson;
    }

    @ApiOperation(value = "Creation of new news")
    @RequestMapping(value = "/admins/{adminId}/news", method = RequestMethod.POST)
    public ResponseEntity add(@PathVariable long adminId, @RequestBody NewsDTO newsDTO) {
        AdminDTO adminDTO = adminService.findById(adminId);
        ResponseEntity responseEntity;
        if (adminDTO == null) {
            responseEntity = notFound("No such admin in database");
        } else {
            NewsDTO dto = newsService.addNews(newsDTO, adminId);
            if (dto == null) {
                responseEntity = internalServerError("Server error");
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(gson.toJson(dto));
            }
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting all client news by client ID")
    @RequestMapping(value = "/client/{clientId}/news", method = RequestMethod.GET)
    public ResponseEntity getAllClientNewsById(@PathVariable long clientId) {
        ClientDTO clientDTO = clientService.findById(clientId);
        ResponseEntity responseEntity;
        if (clientDTO == null) {
            responseEntity = notFound("No such client in database");
        } else {
            Collection<NewsDTO> dtos = newsService.getAllClientNewsById(clientId);
            if (dtos == null) {
                responseEntity = internalServerError("Server error");
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(gson.toJson(dtos));
            }
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting news by ID")
    @RequestMapping(value = "/news/{newsId}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable long newsId) {
        ResponseEntity responseEntity;
        NewsDTO dto = newsService.findById(newsId);
        if (dto == null) {
            responseEntity = notFound("No such news in database");
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(gson.toJson(dto));
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting all news by status")
    @RequestMapping(value = "/admins/news", method = RequestMethod.GET)
    public ResponseEntity getCollection(@RequestParam(name = "filter",
            required = false, defaultValue = "false") boolean filter, @RequestParam(name = "client",
            required = false, defaultValue = "false") boolean client) {
        Collection<NewsDTO> dtos;
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
            responseEntity = internalServerError("Server error");
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(gson.toJson(dtos.isEmpty() ? "Empty list of news" : dtos));
        }
        return responseEntity;
    }

    @ApiOperation(value = "Sending news to clients")
    @RequestMapping(value = "/admins/news/{newsId}", method = RequestMethod.POST)
    public ResponseEntity sendNewsToClients(@PathVariable long newsId,
                                            @RequestBody Collection<Long> clientIds) {
        NewsDTO newsDTO = newsService.findById(newsId);
        ResponseEntity responseEntity;
        if (newsDTO == null) {
            responseEntity = notFound("No such news in database");
        } else {
            NewsDTO dto = newsService.addClientNews(clientIds, newsId);
            if (dto == null) {
                responseEntity = internalServerError("Server error");
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(gson.toJson(dto));
            }
        }
        return responseEntity;
    }

    @ApiOperation(value = "Remarking news")
    @RequestMapping(value = "/admins/news/{newsId}", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody NewsDTO requestNewsDTO, @PathVariable long newsId) {
        ResponseEntity responseEntity;
        NewsDTO newsDTO = newsService.findById(newsId);
        if (newsDTO == null) {
            responseEntity = notFound("No such news in database");
        } else {
            requestNewsDTO.setId(newsId);
            NewsDTO dto = newsService.update(requestNewsDTO);
            if (dto == null) {
                responseEntity = internalServerError("Server error");
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(gson.toJson(dto));
            }
        }
        return responseEntity;
    }

    private ResponseEntity notFound(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gson.toJson(message));
    }

    private ResponseEntity internalServerError(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gson.toJson(message));
    }
}
