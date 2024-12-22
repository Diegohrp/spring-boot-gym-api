package com.diegohrp.gymapi.actuator;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CustomMetrics {

    private final MeterRegistry meterRegistry;
    private Counter customCounter;

    @Autowired
    public CustomMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @PostConstruct
    public void init() {
        customCounter = Counter.builder("custom_metric_counter")
                .description("A custom metric counter")
                .register(meterRegistry);
    }

    public void incrementCounter() {
        customCounter.increment();
    }
}
