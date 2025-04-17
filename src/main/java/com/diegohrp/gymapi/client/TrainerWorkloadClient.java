package com.diegohrp.gymapi.client;

import com.diegohrp.gymapi.dto.trainer.TrainerWorkloadDto;
import com.diegohrp.gymapi.logging.LoggingService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class TrainerWorkloadClient {
    private final RestTemplate restTemplate;
    private final LoggingService logging;

    @CircuitBreaker(name = "trainingHoursService", fallbackMethod = "fallbackSendWorkload")
    public void send(TrainerWorkloadDto workloadDto) {
        restTemplate.postForEntity(
                "http://training-hours-service/training-hours/api/v1/trainers/training-hours",
                workloadDto,
                Void.class
        );
    }

    public void fallbackSendWorkload(TrainerWorkloadDto workloadDto, Throwable ex) {
        logging.error(ex.getMessage());
    }
}
