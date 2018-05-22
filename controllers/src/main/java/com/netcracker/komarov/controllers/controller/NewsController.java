package com.netcracker.komarov.controllers.controller;

import com.netcracker.komarov.services.dto.entity.NewsDTO;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.interfaces.AdminService;
import com.netcracker.komarov.services.interfaces.ClientService;
import com.netcracker.komarov.services.json.NewsJson;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/bank/v1")
public class NewsController {
    private final ClientService clientService;
    private RestTemplate restTemplate;
    private static final String SERVER_PREFIX = "http://localhost:8081/bank/v1";
    private AdminService adminService;

    @Autowired
    public NewsController(RestTemplate restTemplate, AdminService adminService, ClientService clientService) {
        this.restTemplate = restTemplate;
        this.adminService = adminService;
        this.clientService = clientService;
    }

    @ApiOperation(value = "Creation of new news")
    @RequestMapping(value = "/admins/{adminId}/news", method = RequestMethod.POST)
    public ResponseEntity add(@PathVariable long adminId, @RequestBody NewsDTO newsDTO) {
        Map<String, Long> vars = new HashMap<>();
        vars.put("adminId", adminId);
        String url = SERVER_PREFIX + "/admins/{adminId}/news";
        HttpEntity request = new HttpEntity<>(newsDTO);
        ResponseEntity responseEntity;
        try {
            adminService.findById(adminId);
            ResponseEntity<NewsJson> temp = restTemplate.postForEntity(url, request, NewsJson.class, vars);
            responseEntity = convertSingleNewsJson(temp);
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting all client news by client ID")
    @RequestMapping(value = "/clients/{clientId}/news", method = RequestMethod.GET)
    public ResponseEntity getAllClientNewsById(@PathVariable long clientId) {
        Map<String, Long> vars = new HashMap<>();
        vars.put("clientId", clientId);
        String url = SERVER_PREFIX + "/clients/{clientId}/news";
        ResponseEntity responseEntity;
        try {
            clientService.findById(clientId);
            ResponseEntity<NewsJson[]> temp = restTemplate.getForEntity(url, NewsJson[].class, vars);
            responseEntity = convertMultipleNewsJson(temp);
        } catch (HttpStatusCodeException e) {
            responseEntity = getExceptionFromNewsMicroservice(e);
        } catch (NotFoundException e) {
            responseEntity = getNotFoundResponseEntity(e.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting all general news")
    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public ResponseEntity findAllGeneralNews() {
        String url = SERVER_PREFIX + "/news";
        ResponseEntity<NewsJson[]> responseEntity = restTemplate.getForEntity(url, NewsJson[].class);
        return convertMultipleNewsJson(responseEntity);
    }

    @ApiOperation(value = "Select news by ID")
    @RequestMapping(value = "/news/{newsId}", method = RequestMethod.GET)
    public ResponseEntity findGeneralNewsById(@PathVariable long newsId) {
        Map<String, Long> vars = new HashMap<>();
        vars.put("newsId", newsId);
        String url = SERVER_PREFIX + "/news/{newsId}";
        ResponseEntity responseEntity;
        try {
            ResponseEntity<NewsJson> temp = restTemplate.getForEntity(url, NewsJson.class, vars);
            responseEntity = convertSingleNewsJson(temp);
        } catch (HttpStatusCodeException e) {
            responseEntity = getExceptionFromNewsMicroservice(e);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting news by ID")
    @RequestMapping(value = "/admins/news/{newsId}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable long newsId) {
        Map<String, Long> vars = new HashMap<>();
        vars.put("newsId", newsId);
        String url = SERVER_PREFIX + "/admins/news/{newsId}";
        ResponseEntity responseEntity;
        try {
            ResponseEntity<NewsJson> temp = restTemplate.getForEntity(url, NewsJson.class, vars);
            responseEntity = convertSingleNewsJson(temp);
        } catch (HttpStatusCodeException e) {
            responseEntity = getExceptionFromNewsMicroservice(e);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Selecting all news by status")
    @RequestMapping(value = "/admins/news", method = RequestMethod.GET)
    public ResponseEntity getCollection(@RequestParam(name = "filter",
            required = false, defaultValue = "false") boolean filter, @RequestParam(name = "client",
            required = false, defaultValue = "false") boolean client) {
        String url = SERVER_PREFIX + "/admins/news";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("filter", filter)
                .queryParam("client", client);
        ResponseEntity<NewsJson[]> responseEntity = restTemplate.getForEntity(builder.build().toUri(), NewsJson[].class);
        return convertMultipleNewsJson(responseEntity);
    }

    @ApiOperation(value = "Sending news to clients")
    @RequestMapping(value = "/admins/news/{newsId}", method = RequestMethod.POST)
    public ResponseEntity sendNewsToClients(@PathVariable long newsId, @RequestBody Collection<Long> clientIds) {
        Map<String, Long> vars = new HashMap<>();
        vars.put("newsId", newsId);
        String url = SERVER_PREFIX + "/admins/news/{newsId}";
        HttpEntity request = new HttpEntity<>(clientIds);
        ResponseEntity responseEntity;
        try {
            ResponseEntity<NewsJson> temp = restTemplate.exchange(url, HttpMethod.POST,
                    request, NewsJson.class, vars);
            responseEntity = convertSingleNewsJson(temp);
        } catch (HttpStatusCodeException e) {
            responseEntity = getExceptionFromNewsMicroservice(e);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Remarking news")
    @RequestMapping(value = "/admins/news/{newsId}", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody NewsDTO requestNewsDTO, @PathVariable long newsId) {
        Map<String, Long> vars = new HashMap<>();
        vars.put("newsId", newsId);
        String url = SERVER_PREFIX + "/admins/news/{newsId}";
        HttpEntity request = new HttpEntity<>(requestNewsDTO);
        ResponseEntity responseEntity;
        try {
            ResponseEntity<NewsJson> temp = restTemplate.exchange(url, HttpMethod.PUT,
                    request, NewsJson.class, vars);
            responseEntity = convertSingleNewsJson(temp);
        } catch (HttpStatusCodeException e) {
            responseEntity = getExceptionFromNewsMicroservice(e);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Delete news by ID")
    @RequestMapping(value = "/admins/news/{newsId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable long newsId) {
        String url = SERVER_PREFIX + "/admins/news/{newsId}";
        Map<String, Long> vars = new HashMap<>();
        vars.put("newsId", newsId);
        ResponseEntity responseEntity;
        try {
            HttpEntity httpEntity = new HttpEntity<>(null);
            ResponseEntity<Void> temp = restTemplate.exchange(url, HttpMethod.DELETE, httpEntity, Void.class, vars);
            responseEntity = ResponseEntity.status(temp.getStatusCode()).build();
        } catch (HttpStatusCodeException e) {
            responseEntity = getExceptionFromNewsMicroservice(e);
        }
        return responseEntity;
    }

    private ResponseEntity<NewsDTO> convertSingleNewsJson(ResponseEntity<NewsJson> responseEntity) {
        return ResponseEntity.status(responseEntity.getStatusCode()).body(new NewsDTO(responseEntity.getBody()));
    }

    private ResponseEntity<Collection<NewsDTO>> convertMultipleNewsJson(ResponseEntity<NewsJson[]> responseEntity) {
        return ResponseEntity.status(responseEntity.getStatusCode())
                .body(Stream.of(responseEntity.getBody()).map(NewsDTO::new).collect(Collectors.toList()));
    }

    private ResponseEntity getExceptionFromNewsMicroservice(HttpStatusCodeException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    }

    private ResponseEntity getNotFoundResponseEntity(String exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception);
    }
}
