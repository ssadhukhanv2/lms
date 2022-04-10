package com.ssadhukhanv2.lms.librarymanagementsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
@DisplayName("Integration Test for BookService")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Disabled
class BookControllerIntegrationTest {

    @Test
    void happyPath() {

    }

}