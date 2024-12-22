package com.diegohrp.gymapi.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {

        boolean healthCheck = checkCustomHealth();
        if (healthCheck) {
            return Health.up().withDetail("Custom Health Indicator", "Service is healthy").build();
        } else {
            return Health.down().withDetail("Custom Health Indicator", "Service is not healthy").build();
        }
    }

    private boolean checkCustomHealth() {
        return true;
    }
}
