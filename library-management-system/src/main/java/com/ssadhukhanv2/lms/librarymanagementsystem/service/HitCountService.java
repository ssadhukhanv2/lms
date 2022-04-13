package com.ssadhukhanv2.lms.librarymanagementsystem.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class HitCountService {
    private Map<String, Map<Integer, Integer>> hitMap;

    Counter hitCounter;

    public HitCountService(MeterRegistry meterRegistry) {
        hitMap = new ConcurrentHashMap<>();

        //Adds a Counter metric "hit_counter_total" in Prometheus
        //rate(hit_counter_total[5m]) this gives a more smoothed view of the hit_counter when viewed in graph
        //basically rate displays data over a time period
        //https://prometheus.io/docs/prometheus/latest/querying/functions/#rate
        hitCounter = Counter.builder("hit_counter")
                .description("Number of hits")
                .register(meterRegistry);
    }

    public void updateHits(String request, Integer responseCode) {
        Map<Integer, Integer> statusMap = hitMap.get(request);
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
        hitMap.put(request, statusMap);
    }

    public Map<String, Map<Integer, Integer>> getHitMap() {
        return hitMap;
    }
}
