package com.diegohrp.gymapi.controller;

import com.diegohrp.gymapi.dto.trainings.CreateTrainingDto;
import com.diegohrp.gymapi.service.TrainingService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/trainings")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingService service;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createTraining(@RequestBody @Valid CreateTrainingDto trainingDto) {
        try {
            service.create(trainingDto);
            Map<String, Object> resp = new HashMap<>();
            resp.put("status", HttpStatus.CREATED.value());
            resp.put("message", "Training successfully created");
            return new ResponseEntity<>(resp, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
