package com.ssadhukhanv2.lms.librarymanagementsystem.filter;

import com.ssadhukhanv2.lms.librarymanagementsystem.event.ApiEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@Slf4j
@Component
@Order(1)
public class HitCountFilter implements Filter {

    private final ApplicationEventPublisher applicationEventPublisher;

    public HitCountFilter(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }


    /**
     * Publishes the {@link ApiEvent} to {@link ApplicationEventPublisher} for every api calls to <strong>library-management-system</strong><br/>
     * Actuator calls are not logged<br/>
     *
     * This is later used to populate the metric in the actuator <br/>
     *
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        filterChain.doFilter(servletRequest, servletResponse);

        ApiEvent apiEvent = new ApiEvent(httpServletRequest.getMethod(), httpServletRequest.getRequestURI(),
                ((HttpServletResponse) servletResponse).getStatus());

        String request = httpServletRequest.getMethod() + " " + httpServletRequest.getRequestURI();

        // we don't want to log the calls with actuator so it from logging
        if (!request.contains("actuator")) {
            applicationEventPublisher.publishEvent(apiEvent);
        }

        //Since we have implemented Async Listener,
        // Publisher block(HitCountFilter.doFilter) finishes executing even before the Listeners finish processing the events
        log.info("From Publisher: apiEvent="+apiEvent);
        // If the default option of synchronous events were used, logs would be as follows
        // first the listeners finishes processing the events, then the publisher block(HitCountFilter.doFilter) finishes(blocking)

    }
}
