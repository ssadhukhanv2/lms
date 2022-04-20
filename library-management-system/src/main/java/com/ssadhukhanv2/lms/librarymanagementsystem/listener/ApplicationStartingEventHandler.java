package com.ssadhukhanv2.lms.librarymanagementsystem.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

@Slf4j
public class ApplicationStartingEventHandler implements ApplicationListener<ApplicationStartingEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartingEvent applicationEvent) {
        log.info("Handle Spring Boot Event: " + applicationEvent.getClass().getSimpleName());
    }
}
