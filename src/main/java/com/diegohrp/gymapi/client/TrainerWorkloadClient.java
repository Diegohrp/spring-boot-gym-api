package com.diegohrp.gymapi.client;

import com.diegohrp.gymapi.dto.trainer.TrainerWorkloadDto;
import com.diegohrp.gymapi.logging.LoggingService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@AllArgsConstructor
public class TrainerWorkloadClient {
    private final RestTemplate restTemplate;
    private final LoggingService logging;

    @CircuitBreaker(name = "trainingHoursService", fallbackMethod = "fallbackSendWorkload")
    public void send(TrainerWorkloadDto workloadDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-Transaction-Id", MDC.get("transactionId"));
        HttpEntity<TrainerWorkloadDto> request = new HttpEntity<>(workloadDto, headers);

        restTemplate.postForEntity(
                "http://training-hours-service/training-hours/api/v1/trainers/training-hours",
                request,
                Void.class
        );
    }

    public void fallbackSendWorkload(TrainerWorkloadDto workloadDto, Throwable ex) {
        logging.error(ex.getMessage());
    }
}
