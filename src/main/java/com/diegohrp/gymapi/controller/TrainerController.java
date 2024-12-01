package com.diegohrp.gymapi.controller;

import com.diegohrp.gymapi.dto.trainee.TraineeProfileDto;
import com.diegohrp.gymapi.dto.trainer.CreateTrainerDto;
import com.diegohrp.gymapi.dto.trainer.TrainerProfileDto;
import com.diegohrp.gymapi.dto.user.UserCreatedDto;
import com.diegohrp.gymapi.entity.user.Trainee;
import com.diegohrp.gymapi.entity.user.Trainer;
import com.diegohrp.gymapi.mapper.TrainerMapper;
import com.diegohrp.gymapi.mapper.UserMapper;
import com.diegohrp.gymapi.service.TrainerService;
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
@RequestMapping("/trainers")
@RequiredArgsConstructor
public class TrainerController {
    private final TrainerService service;
    private final TrainingService trainingService;
    private final UserMapper userMapper;
    private final TrainerMapper trainerMapper;

    @PostMapping
    public ResponseEntity<UserCreatedDto> create(@RequestBody @Valid CreateTrainerDto dto) {
        try {
            Trainer trainer = service.create(dto);
            return new ResponseEntity<>(userMapper.toCreatedUserDto(trainer.getUser()), HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<TrainerProfileDto> getTraineeProfile(@PathVariable String username) {
        try {
            Trainer trainer = service.getByUsername(username);
            List<Trainee> trainers = trainingService.getTrainees(trainer.getId());
            return new ResponseEntity<>(
                    trainerMapper.toTrainerProfileDto(trainer, trainers),
                    HttpStatus.OK
            );

        } catch (EntityNotFoundException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
