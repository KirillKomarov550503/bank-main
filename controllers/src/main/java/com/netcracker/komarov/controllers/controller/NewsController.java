package com.netcracker.komarov.controllers.controller;

import com.netcracker.komarov.controllers.client.NewsClient;
import com.netcracker.komarov.services.dto.entity.NewsDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/bank/v1")
public class NewsController {
    private NewsClient newsClient;

    @Autowired
    public NewsController(NewsClient newsClient) {
        this.newsClient = newsClient;
    }

    @ApiOperation(value = "Create of new news")
    @RequestMapping(value = "/admins/{personId}/news", method = RequestMethod.POST)
    public ResponseEntity save(@PathVariable long personId, @RequestBody NewsDTO newsDTO) {
        return newsClient.save(personId, newsDTO);
    }

    @ApiOperation(value = "Select all client news by client ID")
    @RequestMapping(value = "/clients/{personId}/news", method = RequestMethod.GET)
    public ResponseEntity findNewsByClientId(@PathVariable long personId) {
        return newsClient.findNewsByClientId(personId);
    }

    @ApiOperation(value = "Select all general news")
    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public ResponseEntity findAllGeneralNews() {
        return newsClient.findAllGeneralNews();
    }

    @ApiOperation(value = "Select general news by ID")
    @RequestMapping(value = "/news/{newsId}", method = RequestMethod.GET)
    public ResponseEntity findGeneralNewsById(@PathVariable long newsId) {
        return newsClient.findGeneralNewsById(newsId);
    }

    @ApiOperation(value = "Select news by ID")
    @RequestMapping(value = "/admins/news/{newsId}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable long newsId) {
        return newsClient.findById(newsId);
    }

    @ApiOperation(value = "Select news by specification")
    @RequestMapping(value = "/admins/news", method = RequestMethod.GET)
    public ResponseEntity findNewsByParams(@RequestParam() Map<String, String> parameters) {
        return newsClient.findNewsByParams(parameters);
    }

    @ApiOperation(value = "Send news to clients")
    @RequestMapping(value = "/admins/news/{newsId}", method = RequestMethod.POST)
    public ResponseEntity sendNewsToClients(@PathVariable long newsId, @RequestBody Collection<Long> clientIds) {
        return newsClient.sendNewsToClients(newsId, clientIds);
    }

    @ApiOperation(value = "Update news")
    @RequestMapping(value = "/admins/news/{newsId}", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody NewsDTO requestNewsDTO, @PathVariable long newsId) {
        return newsClient.update(requestNewsDTO, newsId);
    }

    @ApiOperation(value = "Delete news by ID")
    @RequestMapping(value = "/admins/news/{newsId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable long newsId) {
        return newsClient.deleteById(newsId);
    }
}
