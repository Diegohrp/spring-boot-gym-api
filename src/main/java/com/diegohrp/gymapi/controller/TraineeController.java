package com.diegohrp.gymapi.controller;

import com.diegohrp.gymapi.dto.trainee.CreateTraineeDto;
import com.diegohrp.gymapi.dto.trainee.TraineeProfileDto;
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

import java.util.List;


@RestController
@RequestMapping("/trainees")
@RequiredArgsConstructor
public class TraineeController {
    private final TraineeService service;
    private final TrainingService trainingService;
    private final UserMapper userMapper;
    private final TraineeMapper traineeMapper;


    @PostMapping
    public ResponseEntity<UserCreatedDto> create(@RequestBody @Valid CreateTraineeDto traineeDto) {
        Trainee trainee = service.create(traineeDto);
        return new ResponseEntity<>(userMapper.toCreatedUserDto(trainee.getUser()), HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<TraineeProfileDto> getTraineeProfile(@PathVariable String username) {
        try {
            Trainee trainee = service.getByUsername(username);
            List<Trainer> trainers = trainingService.getTrainers(trainee.getId());
            return new ResponseEntity<>(traineeMapper.toTraineeProfileDto(trainee, trainers), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
