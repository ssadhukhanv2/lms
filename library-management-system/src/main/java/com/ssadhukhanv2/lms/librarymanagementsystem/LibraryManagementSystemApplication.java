package com.ssadhukhanv2.lms.librarymanagementsystem;

import com.ssadhukhanv2.lms.librarymanagementsystem.dao.BookDao;
import com.ssadhukhanv2.lms.librarymanagementsystem.exception.LibraryOperationException;
import com.ssadhukhanv2.lms.librarymanagementsystem.factory.BookDaoFactory;
import com.ssadhukhanv2.lms.librarymanagementsystem.service.BookService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@EnableEurekaClient
public class LibraryManagementSystemApplication implements CommandLineRunner {

    @Autowired
    BookService bookService;


    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementSystemApplication.class, args);
    }

    //https://stackoverflow.com/questions/59115578/httptrace-endpoint-of-spring-boot-actuator-doesnt-exist-anymore-with-spring-b
    // actuator/httptrace no longer available by default with Spring Boot 2.2.0

    //    Interesting Actuator Endpoints
    //    http://localhost:8080/actuator/conditions
    //    http://localhost:8080/actuator/configprops
    //    http://localhost:8080/actuator/httptrace
    @Bean
    public HttpTraceRepository loadHttpTraceRepository() {
        return new InMemoryHttpTraceRepository();
    }

    @Override
    public void run(String... args) throws Exception {
        BookDaoFactory.createDefaultBookDaoList().forEach((bookDao) -> {
            try {
                bookService.createBook(bookDao);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
