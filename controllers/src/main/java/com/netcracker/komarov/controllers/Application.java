package com.netcracker.komarov.controllers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.netcracker.komarov")
@EnableAutoConfiguration
@EnableJpaRepositories("com.netcracker.komarov.dao.repository")
@EntityScan(basePackages = "com.netcracker.komarov.dao.entity")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
