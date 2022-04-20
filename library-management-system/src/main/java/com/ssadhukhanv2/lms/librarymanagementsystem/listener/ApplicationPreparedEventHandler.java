package com.ssadhukhanv2.lms.librarymanagementsystem.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;

@Slf4j
public class ApplicationPreparedEventHandler implements ApplicationListener<ApplicationPreparedEvent> {
    @Override
    public void onApplicationEvent(ApplicationPreparedEvent applicationEvent) {
        log.info("Handle Spring Boot Event: " + applicationEvent.getClass().getSimpleName());
    }
}
