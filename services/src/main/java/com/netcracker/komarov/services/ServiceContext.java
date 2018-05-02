package com.netcracker.komarov.services;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = "com.netcracker.komarov")
@EnableJpaRepositories("com.netcracker.komarov.dao.repository")
@EntityScan("com.netcracker.komarov.dao.entity")
public class ServiceContext {
}
