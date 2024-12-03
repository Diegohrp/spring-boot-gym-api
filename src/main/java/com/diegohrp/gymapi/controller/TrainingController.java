package com.diegohrp.gymapi.controller;

import com.diegohrp.gymapi.dto.trainings.CreateTrainingDto;
import com.diegohrp.gymapi.dto.trainings.TrainingTypeDto;
import com.diegohrp.gymapi.mapper.TrainingMapper;
import com.diegohrp.gymapi.service.TrainingService;
import com.diegohrp.gymapi.service.TrainingTypeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trainings")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingService service;
    private final TrainingTypeService typeService;
    private final TrainingMapper mapper;

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

    @GetMapping("/types")
    public ResponseEntity<List<TrainingTypeDto>> getTrainingTypes() {
        return new ResponseEntity<>(
                mapper.toTrainingTypesList(typeService.getAll()),
                HttpStatus.OK
        );
    }
}
