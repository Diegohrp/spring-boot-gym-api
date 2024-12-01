package com.diegohrp.gymapi.controller;

import com.diegohrp.gymapi.dto.trainee.CreateTraineeDto;
import com.diegohrp.gymapi.dto.trainee.TraineeProfileDto;
import com.diegohrp.gymapi.dto.trainee.UpdateTraineeDto;
import com.diegohrp.gymapi.dto.user.UserCreatedDto;
import com.diegohrp.gymapi.entity.user.Trainee;
import com.diegohrp.gymapi.entity.user.Trainer;
import com.diegohrp.gymapi.mapper.TraineeMapper;
import com.diegohrp.gymapi.mapper.UserMapper;
import com.diegohrp.gymapi.service.TraineeService;
import com.diegohrp.gymapi.service.TrainingService;
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
@RequestMapping("/trainees")
@RequiredArgsConstructor
public class TraineeController {
    private final TraineeService service;
    private final TrainingService trainingService;
    private final UserMapper userMapper;
    private final TraineeMapper traineeMapper;
    private final TraineeService traineeService;


    @PostMapping
    public ResponseEntity<UserCreatedDto> create(@RequestBody @Valid CreateTraineeDto traineeDto) {
        Trainee trainee = service.create(traineeDto);
        return new ResponseEntity<>(
                userMapper.toCreatedUserDto(trainee.getUser()),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{username}")
    public ResponseEntity<TraineeProfileDto> getTraineeProfile(@PathVariable String username) {
        try {
            Trainee trainee = service.getByUsername(username);
            List<Trainer> trainers = trainingService.getTrainers(trainee.getId());
            return new ResponseEntity<>(
                    traineeMapper.toTraineeProfileDto(trainee, trainers),
                    HttpStatus.OK
            );

        } catch (EntityNotFoundException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{username}")
    public ResponseEntity<TraineeProfileDto> updateTraineeProfile(@PathVariable String username, @RequestBody @Valid UpdateTraineeDto traineeDto) {
        try {
            Trainee trainee = traineeService.update(username, traineeDto);
            List<Trainer> trainers = trainingService.getTrainers(trainee.getId());
            return new ResponseEntity<>(
                    traineeMapper.toTraineeProfileDto(trainee, trainers),
                    HttpStatus.OK
            );

        } catch (EntityNotFoundException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Map<String, Object>> deleteTrainee(@PathVariable String username) {
        try {
            service.delete(username);
            Map<String, Object> resp = new HashMap<>();
            resp.put("status", HttpStatus.OK.value());
            resp.put("message", "User: " + username + " successfully deleted");
            return new ResponseEntity<>(resp, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
