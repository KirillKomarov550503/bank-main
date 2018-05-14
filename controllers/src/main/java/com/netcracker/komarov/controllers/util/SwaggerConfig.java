package com.netcracker.komarov.controllers.util;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private Predicate<String> apiPaths() {
        return Predicates.or(PathSelectors.regex("/bank/.*"));
    }

    ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Internet banking")
                .description("Project for passage in Netcracker")
                .license("No license").licenseUrl("http://unlicense.org").termsOfServiceUrl("").version("1.0")
                .contact(new Contact("Kirill Komarov", "", "kirya.komarov.97@list.ru")).build();
    }

    @Bean
    public Docket dockect() {
        List<SecurityReference> references = Collections.singletonList(new SecurityReference("bank",
                Stream.of(new AuthorizationScope("", ""))
                        .toArray(AuthorizationScope[]::new)));
        List<SecurityContext> securityContexts = Collections
                .singletonList(SecurityContext.builder().securityReferences(references).build());
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Bank")
                .securitySchemes(Collections.singletonList(new BasicAuth("bank")))
                .securityContexts(securityContexts)
                .apiInfo(apiInfo())
                .select()
                .paths(apiPaths())
                .apis(RequestHandlerSelectors.basePackage("com.netcracker.komarov"))
                .build();
    }
}
