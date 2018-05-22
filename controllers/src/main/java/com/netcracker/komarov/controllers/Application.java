package com.netcracker.komarov.controllers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = "com.netcracker.komarov")
@EnableJpaRepositories("com.netcracker.komarov.dao.repository")
@EntityScan(basePackages = "com.netcracker.komarov.dao.entity")
public class Application {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
