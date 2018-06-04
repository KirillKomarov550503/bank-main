package com.netcracker.komarov.controllers.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.komarov.services.dto.entity.NewsDTO;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.interfaces.PersonService;
import com.netcracker.komarov.services.util.MapConverter;
import com.netcracker.komarov.services.util.UriBuilder;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/bank/v1")
public class NewsController {
    private RestTemplate restTemplate;
    private PersonService personService;
    private EurekaClient eurekaClient;
    private UriBuilder uriBuilder;
    private MapConverter mapConverter;
    private ObjectMapper objectMapper;

    @Autowired
    public NewsController(RestTemplate restTemplate, PersonService personService, ObjectMapper objectMapper,
                          UriBuilder uriBuilder, MapConverter mapConverter,
                          @Qualifier("eurekaClient") EurekaClient eurekaClient) {
        this.restTemplate = restTemplate;
        this.personService = personService;
        this.eurekaClient = eurekaClient;
        this.uriBuilder = uriBuilder;
        this.mapConverter = mapConverter;
        this.objectMapper = objectMapper;
    }

    @ApiOperation(value = "Creation of new news")
    @RequestMapping(value = "/admins/{personId}/news", method = RequestMethod.POST)
    public ResponseEntity save(@PathVariable long personId, @RequestBody NewsDTO newsDTO) {
        Map<String, Long> vars = new HashMap<>();
        vars.put("adminId", personId);
        String url = getDomain() + "/admins/{adminId}/news";
        HttpEntity request = new HttpEntity<>(newsDTO);
        ResponseEntity responseEntity;
        try {
            personService.findById(personId);
            responseEntity = restTemplate.postForEntity(url, request, NewsDTO.class, vars);
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        } catch (HttpStatusCodeException e) {
            responseEntity = getExceptionFromNewsService(e);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting all client news by client ID")
    @RequestMapping(value = "/clients/{personId}/news", method = RequestMethod.GET)
    public ResponseEntity findNewsByClientId(@PathVariable long personId) {
        Map<String, Long> vars = new HashMap<>();
        vars.put("clientId", personId);
        String url = getDomain() + "/clients/{clientId}/news";
        ResponseEntity responseEntity;
        try {
            personService.findById(personId);
            responseEntity = restTemplate.getForEntity(url, NewsDTO[].class, vars);
        } catch (HttpStatusCodeException e) {
            responseEntity = getExceptionFromNewsService(e);
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting all general news")
    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public ResponseEntity findAllGeneralNews() {
        String url = getDomain() + "/news";
        ResponseEntity responseEntity;
        try {
            responseEntity = restTemplate.getForEntity(url, NewsDTO[].class);
        } catch (HttpStatusCodeException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Select news by ID")
    @RequestMapping(value = "/news/{newsId}", method = RequestMethod.GET)
    public ResponseEntity findGeneralNewsById(@PathVariable long newsId) {
        Map<String, Long> vars = new HashMap<>();
        vars.put("newsId", newsId);
        String url = getDomain() + "/news/{newsId}";
        ResponseEntity responseEntity;
        try {
            responseEntity = restTemplate.getForEntity(url, NewsDTO.class, vars);
        } catch (HttpStatusCodeException e) {
            responseEntity = getExceptionFromNewsService(e);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting news by ID")
    @RequestMapping(value = "/admins/news/{newsId}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable long newsId) {
        Map<String, Long> vars = new HashMap<>();
        vars.put("newsId", newsId);
        String url = getDomain() + "/admins/news/{newsId}";
        ResponseEntity responseEntity;
        try {
            responseEntity = restTemplate.getForEntity(url, NewsDTO.class, vars);
        } catch (HttpStatusCodeException e) {
            responseEntity = getExceptionFromNewsService(e);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting news by specification")
    @RequestMapping(value = "/admins/news", method = RequestMethod.GET)
    public ResponseEntity findNewsByParams(@RequestParam() Map<String, String> parameters) {
        String url = getDomain() + "/admins/news";
        ResponseEntity responseEntity;
        try {
            responseEntity = restTemplate.getForEntity(uriBuilder.build(url, mapConverter.convert(parameters)),
                    NewsDTO[].class);
        } catch (HttpStatusCodeException e) {
            responseEntity = getExceptionFromNewsService(e);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Sending news to clients")
    @RequestMapping(value = "/admins/news/{newsId}", method = RequestMethod.POST)
    public ResponseEntity sendNewsToClients(@PathVariable long newsId, @RequestBody Collection<Long> clientIds) {
        Map<String, Long> vars = new HashMap<>();
        vars.put("newsId", newsId);
        String url = getDomain() + "/admins/news/{newsId}";
        HttpEntity request = new HttpEntity<>(clientIds);
        ResponseEntity responseEntity;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.POST, request, NewsDTO.class, vars);
        } catch (HttpStatusCodeException e) {
            responseEntity = getExceptionFromNewsService(e);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Remarking news")
    @RequestMapping(value = "/admins/news/{newsId}", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody NewsDTO requestNewsDTO, @PathVariable long newsId) {
        Map<String, Long> vars = new HashMap<>();
        vars.put("newsId", newsId);
        String url = getDomain() + "/admins/news/{newsId}";
        HttpEntity request = new HttpEntity<>(requestNewsDTO);
        ResponseEntity responseEntity;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.PUT, request, NewsDTO.class, vars);
        } catch (HttpStatusCodeException e) {
            responseEntity = getExceptionFromNewsService(e);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Delete news by ID")
    @RequestMapping(value = "/admins/news/{newsId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable long newsId) {
        String url = getDomain() + "/admins/news/{newsId}";
        Map<String, Long> vars = new HashMap<>();
        vars.put("newsId", newsId);
        ResponseEntity responseEntity;
        try {
            HttpEntity httpEntity = new HttpEntity<>(null);
            responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, httpEntity, Void.class, vars);
        } catch (HttpStatusCodeException e) {
            responseEntity = getExceptionFromNewsService(e);
        }
        return responseEntity;
    }

    private String getDomain() {
        Application application = eurekaClient.getApplication("bank-news");
        InstanceInfo info = application.getInstances().get(0);
        return "http://" + info.getIPAddr() + ":" + info.getPort() + "/bank/v1";
    }

    private ResponseEntity getExceptionFromNewsService(HttpStatusCodeException e) {
        return ResponseEntity.status(e.getStatusCode()).body(objectMapper.valueToTree(e.getResponseBodyAsString()));
    }

    private ResponseEntity getNotFoundResponseEntity(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(objectMapper.valueToTree(message));
    }
}
