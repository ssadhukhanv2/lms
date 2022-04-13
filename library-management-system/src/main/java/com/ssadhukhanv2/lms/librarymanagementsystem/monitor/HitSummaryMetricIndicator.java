package com.ssadhukhanv2.lms.librarymanagementsystem.monitor;

import com.ssadhukhanv2.lms.librarymanagementsystem.service.HitCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component("hit_summary")
public class HitSummaryMetricIndicator implements HealthIndicator {


    /**
     *
     * @see <a href="https://www.baeldung.com/spring-rest-api-metrics">REST API Metrics</a>
     */

    @Autowired
    HitCountService hitCountService;
    /*
     * Custom Health Indicator named "hit_summary" for actuator/health
     *
     * "hit_summary": {
      "status": "UP",
      "details": {
        "GET /actuator/health": {
          "200": 1
        },
        "POST /api/v1/book/search/authors": {
          "200": 7
        }
      }
    },
     * http://localhost:8080/actuator/health
     * */

    @Override
    public Health health() {
        return Health.up().withDetails(hitCountService.getHitMap()).build();
    }


}
