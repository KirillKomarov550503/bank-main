package com.netcracker.komarov.controllers.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.komarov.services.dto.entity.NewsDTO;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.interfaces.PersonService;
import com.netcracker.komarov.services.util.MapConverter;
import com.netcracker.komarov.services.util.UriBuilder;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class NewsClient {
    private RestTemplate restTemplate;
    private PersonService personService;
    private EurekaClient eurekaClient;
    private UriBuilder uriBuilder;
    private MapConverter mapConverter;
    private ObjectMapper objectMapper;
    private Environment environment;

    @Autowired
    public NewsClient(RestTemplate restTemplate, PersonService personService, UriBuilder uriBuilder,
                      MapConverter mapConverter, ObjectMapper objectMapper, Environment environment,
                      @Qualifier("eurekaClient") EurekaClient eurekaClient) {
        this.restTemplate = restTemplate;
        this.personService = personService;
        this.eurekaClient = eurekaClient;
        this.uriBuilder = uriBuilder;
        this.mapConverter = mapConverter;
        this.objectMapper = objectMapper;
        this.environment = environment;
    }

    public ResponseEntity save(long personId, NewsDTO newsDTO) {
        Map<String, Long> vars = new HashMap<>();
        vars.put("adminId", personId);
        String url = getDomain() + "/admins/{adminId}/news";
        HttpEntity request = new HttpEntity<>(newsDTO);
        ResponseEntity responseEntity;
        try {
            personService.findById(personId);
            responseEntity = restTemplate.postForEntity(url, request, NewsDTO.class, vars);
        } catch (NotFoundException e) {
            responseEntity = getErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (HttpStatusCodeException e) {
            responseEntity = getExceptionFromNewsService(e);
        }
        return responseEntity;
    }

    public ResponseEntity findNewsByClientId(long personId) {
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
            responseEntity = getErrorResponse(HttpStatus.FORBIDDEN, e.getMessage());
        }
        return responseEntity;
    }

    public ResponseEntity findAllGeneralNews() {
        String url = getDomain() + "/news";
        ResponseEntity responseEntity;
        try {
            responseEntity = restTemplate.getForEntity(url, NewsDTO[].class);
        } catch (HttpStatusCodeException e) {
            responseEntity = getErrorResponse(HttpStatus.FORBIDDEN, environment.getProperty("http.forbidden"));
        }
        return responseEntity;
    }

    public ResponseEntity findGeneralNewsById(long newsId) {
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

    public ResponseEntity findById(long newsId) {
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

    public ResponseEntity findNewsByParams(Map<String, String> parameters) {
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

    public ResponseEntity sendNewsToClients(long newsId, Collection<Long> clientIds) {
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

    public ResponseEntity update(NewsDTO requestNewsDTO, long newsId) {
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

    public ResponseEntity deleteById(long newsId) {
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

    private ResponseEntity getErrorResponse(HttpStatus httpStatus, String message) {
        return ResponseEntity.status(httpStatus).body(objectMapper.valueToTree(message));
    }
}
