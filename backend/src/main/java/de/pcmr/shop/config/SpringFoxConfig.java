package de.pcmr.shop.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Configuration
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("de.pcmr.shop.api.controller"))
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .apiInfo(apiInfo())
                .host("api.pc-mr.de")
                .protocols(Set.of("https"))
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "PCMR REST-API",
                "REST-API documentation of PCMR (PC Masterrace) Webshop",
                "1",
                null,
                new Contact("PC-MR (Fynn Lohse)", "https://pc-mr.de", "fynn.lohse@edu.fhdw.de"),
                null, null, Collections.emptyList());
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .operationSelector(this::isProtected)
                .build();
    }

    List<SecurityReference> defaultAuth() {
        return Lists.newArrayList(
                new SecurityReference("JWT", new AuthorizationScope[0]));
    }

    private boolean isProtected(OperationContext operationContext) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        return antPathMatcher.match("/api/customer/**", operationContext.requestMappingPattern()) ||
                antPathMatcher.match("/api/employee/**", operationContext.requestMappingPattern()) ||
                antPathMatcher.match("/api/admin/**", operationContext.requestMappingPattern());
    }
}
