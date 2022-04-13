package com.ssadhukhanv2.lms.librarymanagementsystem.filter;

import com.ssadhukhanv2.lms.librarymanagementsystem.service.HitCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
public class HitCountFilter implements Filter {

    @Autowired
    HitCountService hitCountService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String request = httpServletRequest.getMethod() + " " + httpServletRequest.getRequestURI();

        filterChain.doFilter(servletRequest, servletResponse);

        int responseStatus = ((HttpServletResponse) servletResponse).getStatus();
        // we don't want to log the calls with actuator so it from logging
        if (!request.contains("actuator")) {
            hitCountService.updateHits(request, responseStatus);
        }
    }
}
