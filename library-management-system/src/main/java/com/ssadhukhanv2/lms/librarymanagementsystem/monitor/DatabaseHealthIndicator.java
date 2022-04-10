package com.ssadhukhanv2.lms.librarymanagementsystem.monitor;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component("database")
public class DatabaseHealthIndicator implements HealthIndicator {

    /*
    * Custom Health Indicator named "database" for actuator/health
    *
    * http://localhost:8080/actuator/health
    * */
    @Override
    public Health health() {
        if (isDatabaseHealthy()) {
            return Health.up().withDetail("External Database Status", "Healthy").build();
        }

        return Health.down().withDetail("External Database Status", "Unhealthy").build();
    }

    private boolean isDatabaseHealthy() {
        Random random = new Random();
        return random.nextBoolean();
    }
}
