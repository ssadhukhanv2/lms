package com.ssadhukhanv2.lms.librarymanagementsystem.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration to generate of API Docs using springdoc-openapi library
 *
 * @author Subhrajit Sadhukhan
 * @see <a href="https://springdoc.org/#Introduction">Springdoc Documentation</a>
 * @see <a href="https://www.baeldung.com/spring-rest-openapi-documentation">Documenting a Spring REST API Using OpenAPI 3.0</a>
 * */
@Configuration
public class SpringdocOpenApiConfig {


    /**
     * Scans and publishes the API docs matching the url pattern "/api/v1/book/"
     */
    @Bean
    public GroupedOpenApi lmsApi() {
        return GroupedOpenApi.builder()
                .group("library-management-system")
                .pathsToMatch("/api/v1/book/**")
                .build();
    }

    /**
     * Summary of the Library Management System API Docs
     * @see <a href="http://localhost:8080/v3/api-docs">View the API Docs</a>
     * @see <a href="http://localhost:8080/v3/api-docs.yaml">Download the API Doc yaml</a>
     * @see <a href="http://localhost:8080/swagger-ui/index.html">Swagger UI</a>
     *
     */
    @Bean
    public OpenAPI lmsOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Library Management System APIs")
                        .description("Library Management System Application has the below functionalities:\n\t1. List Books\n\t2. Search Books based on search criteria\n\t3. CRUD on individual books ")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Library Management System Documentation")
                        .url("http://springdoc.org"));

    }
}
