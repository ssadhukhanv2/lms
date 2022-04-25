package com.ssadhukhanv2.lms.librarymanagementsystem;

import com.ssadhukhanv2.lms.librarymanagementsystem.dao.BookDao;
import com.ssadhukhanv2.lms.librarymanagementsystem.exception.LibraryOperationException;
import com.ssadhukhanv2.lms.librarymanagementsystem.factory.BookDaoFactory;
import com.ssadhukhanv2.lms.librarymanagementsystem.listener.ApplicationContextInitializedEventHandler;
import com.ssadhukhanv2.lms.librarymanagementsystem.listener.ApplicationEnvironmentPreparedEventHandler;
import com.ssadhukhanv2.lms.librarymanagementsystem.listener.ApplicationPreparedEventHandler;
import com.ssadhukhanv2.lms.librarymanagementsystem.listener.ApplicationStartingEventHandler;
import com.ssadhukhanv2.lms.librarymanagementsystem.service.BookService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
@EnableEurekaClient
public class LibraryManagementSystemApplication {

    @Autowired
    BookService bookService;

    @Autowired
    BookDaoFactory bookDaoFactory;

    public static void main(String[] args) {

//        ConfigurableApplicationContext configurableApplicationContext= SpringApplication.run(LibraryManagementSystemApplication.class, args);
        /*
        * The handlers/listeners specificed here handles/listens to events which are triggered before Application Context is created
        * So we register them manually
        * */
        ConfigurableApplicationContext configurableApplicationContext = new SpringApplicationBuilder().sources(LibraryManagementSystemApplication.class)
                .bannerMode(Banner.Mode.CONSOLE)
                .listeners(new ApplicationContextInitializedEventHandler(),
                        new ApplicationEnvironmentPreparedEventHandler(),
                        new ApplicationPreparedEventHandler(),
                        new ApplicationStartingEventHandler())
                .run(args);

        //https://stackoverflow.com/questions/48099355/contextstartedevent-not-firing-in-custom-listener
        //By default configurableApplicationContext.start() is not triggered so listening to ContextStartedEvent doesn't work,
        //so we manually fire configurableApplicationContext.start() to fire ContextStartedEvent so that lissteners start working
        configurableApplicationContext.start();



    }

    @PostConstruct
    public void createInitialSetOfBook() throws Exception {
        this.bookDaoFactory.createDefaultBookDaoList().forEach((bookDao) -> {
            try {
                bookService.createBook(bookDao);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //create using factory method
        bookService.createBook(this.bookDaoFactory.createBookDao("BookDao"));

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


}
