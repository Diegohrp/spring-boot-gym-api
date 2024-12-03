package com.diegohrp.gymapi.controller;


import com.diegohrp.gymapi.dto.trainer.CreateTrainerDto;
import com.diegohrp.gymapi.dto.trainer.TrainerProfileDto;
import com.diegohrp.gymapi.dto.trainer.TrainerSummaryDto;
import com.diegohrp.gymapi.dto.trainer.UpdateTrainerDto;
import com.diegohrp.gymapi.dto.trainings.TraineeTrainingDto;
import com.diegohrp.gymapi.dto.trainings.TrainerTrainingDto;
import com.diegohrp.gymapi.dto.user.UserCreatedDto;
import com.diegohrp.gymapi.entity.training.Training;
import com.diegohrp.gymapi.entity.user.Trainee;
import com.diegohrp.gymapi.entity.user.Trainer;
import com.diegohrp.gymapi.mapper.SummaryMapper;
import com.diegohrp.gymapi.mapper.TrainerMapper;
import com.diegohrp.gymapi.mapper.TrainingMapper;
import com.diegohrp.gymapi.mapper.UserMapper;
import com.diegohrp.gymapi.service.TrainerService;
import com.diegohrp.gymapi.service.TrainingService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/trainers")
@RequiredArgsConstructor
public class TrainerController {
    private final TrainerService service;
    private final TrainingService trainingService;
    private final UserMapper userMapper;
    private final TrainerMapper trainerMapper;
    private final SummaryMapper summaryMapper;
    private final TrainingMapper trainingMapper;

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

    @PutMapping("/{username}")
    public ResponseEntity<TrainerProfileDto> updateTraineeProfile(@PathVariable String username, @RequestBody @Valid UpdateTrainerDto trainerDto) {
        try {
            Trainer trainer = service.update(username, trainerDto);
            List<Trainee> trainees = trainingService.getTrainees(trainer.getId());
            return new ResponseEntity<>(
                    trainerMapper.toTrainerProfileDto(trainer, trainees),
                    HttpStatus.OK
            );

        } catch (EntityNotFoundException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/active/unassigned-to")
    public ResponseEntity<List<TrainerSummaryDto>> getUnassignedTrainers(@RequestParam String trainee) {
        List<Trainer> trainers = service.getUnassigned(trainee);
        return new ResponseEntity<>(summaryMapper.toTrainersList(trainers), HttpStatus.OK);
    }

    @GetMapping("/{username}/trainings")
    public ResponseEntity<List<TrainerTrainingDto>> getTrainings(@PathVariable String username,
                                                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date periodFrom,
                                                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date periodTo,
                                                                 @RequestParam(required = false) String traineeName) {
        List<Training> trainings = trainingService.getTrainerTrainings(username, periodFrom, periodTo, traineeName);
        return new ResponseEntity<>(
                trainingMapper.toTrainerTrainings(trainings),
                HttpStatus.OK
        );
    }
}
