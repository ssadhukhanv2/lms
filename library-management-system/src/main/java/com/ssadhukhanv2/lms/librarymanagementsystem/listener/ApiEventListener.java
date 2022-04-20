package com.ssadhukhanv2.lms.librarymanagementsystem.listener;

import com.ssadhukhanv2.lms.librarymanagementsystem.event.ApiEvent;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class ApiEventListener {
    private Map<String, Map<Integer, Integer>> hitMap;

    Counter hitCounter;

    public ApiEventListener(MeterRegistry meterRegistry) {
        hitMap = new ConcurrentHashMap<>();

        //Adds a Counter metric "hit_counter_total" in Prometheus
        //rate(hit_counter_total[5m]) this gives a more smoothed view of the hit_counter when viewed in graph
        //basically rate displays data over a time period
        //https://prometheus.io/docs/prometheus/latest/querying/functions/#rate
        hitCounter = Counter.builder("hit_counter")
                .description("Number of hits")
                .register(meterRegistry);
    }

    // Use Spring events to decouple this
    // https://medium.com/@semotpan/spring-application-events-7ab5390db6dd
    @Async
    @EventListener
    public void handleApiEvent(ApiEvent apiEvent) {
        String requestString = apiEvent.getMethod() + " " + apiEvent.getUri();
        int responseCode = apiEvent.getResponseStatus();
        Map<Integer, Integer> statusMap = hitMap.get(requestString);
        if (statusMap == null) {
            statusMap = new ConcurrentHashMap<>();
        }
        Integer count = statusMap.get(responseCode);
        if (count == null) {
            count = 1;
        } else {
            count++;
        }

        hitCounter.increment();

        statusMap.put(responseCode, count);
        hitMap.put(requestString, statusMap);

        log.info("From Listener handleApiEvent:"+apiEvent.toString());
    }

    @Async
    @EventListener
    public void logApiEventToConsole(ApiEvent apiEvent) {
        log.info("From Listener logApiEventToConsole: "+apiEvent.toString());
    }

    public Map<String, Map<Integer, Integer>> getHitMap() {
        return hitMap;
    }
}
