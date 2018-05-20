package com.netcracker.komarov.controllers.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Internet banking")
                .description("Project for passage in Netcracker")
                .license("No license").licenseUrl("http://unlicense.org").termsOfServiceUrl("").version("1.0")
                .contact(new Contact("Kirill Komarov", "", "kirya.komarov.97@list.ru")).build();
    }

    @Bean
    public Docket dockect() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Bank")
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.netcracker.komarov"))
                .build();
    }
}
