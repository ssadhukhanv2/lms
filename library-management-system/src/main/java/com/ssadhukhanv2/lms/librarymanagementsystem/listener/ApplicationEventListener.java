package com.ssadhukhanv2.lms.librarymanagementsystem.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.*;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
@Slf4j
public class ApplicationEventListener {

    /*
    * https://medium.com/@semotpan/spring-application-events-7ab5390db6dd
    * */
    @EventListener(classes = {ContextRefreshedEvent.class, ContextStartedEvent.class, ContextStoppedEvent.class, ContextClosedEvent.class})
    public void handleSpringCoreEvents(ApplicationEvent applicationEvent) {
        log.info("Handle Spring Core Events: " + applicationEvent.getClass().getSimpleName());
    }

}
