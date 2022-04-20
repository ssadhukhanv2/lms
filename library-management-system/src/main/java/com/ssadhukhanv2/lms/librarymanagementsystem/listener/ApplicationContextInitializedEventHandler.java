package com.ssadhukhanv2.lms.librarymanagementsystem.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

@Slf4j
public class ApplicationContextInitializedEventHandler implements ApplicationListener<ApplicationContextInitializedEvent> {
    @Override
    public void onApplicationEvent(ApplicationContextInitializedEvent applicationEvent) {
        log.info("Handle Spring Boot Event: " + applicationEvent.getClass().getSimpleName());
    }
}
