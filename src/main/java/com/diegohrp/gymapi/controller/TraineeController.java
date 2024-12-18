package com.diegohrp.gymapi.controller;

import com.diegohrp.gymapi.dto.trainings.TraineeTrainingsParams;
import com.diegohrp.gymapi.hateoas.AssemblerManager;
import com.diegohrp.gymapi.dto.trainee.CreateTraineeDto;
import com.diegohrp.gymapi.dto.trainee.TraineeProfileDto;
import com.diegohrp.gymapi.dto.trainee.UpdateTraineeDto;
import com.diegohrp.gymapi.dto.trainings.TraineeTrainingDto;
import com.diegohrp.gymapi.dto.user.UserCreatedDto;
import com.diegohrp.gymapi.entity.training.Training;
import com.diegohrp.gymapi.entity.user.Trainee;
import com.diegohrp.gymapi.entity.user.Trainer;

import com.diegohrp.gymapi.mapper.TraineeMapper;
import com.diegohrp.gymapi.mapper.TrainingMapper;
import com.diegohrp.gymapi.mapper.UserMapper;
import com.diegohrp.gymapi.service.TraineeService;
import com.diegohrp.gymapi.service.TrainingService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;


import java.util.Date;
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
    private final TrainingMapper trainingMapper;
    private final TraineeService traineeService;
    private final AssemblerManager assemblerManager;


    @PostMapping
    public ResponseEntity<EntityModel<UserCreatedDto>> create(@RequestBody @Valid CreateTraineeDto traineeDto) {
        Trainee trainee = service.create(traineeDto);
        UserCreatedDto userCreatedDto = userMapper.toCreatedUserDto(trainee.getUser());

        EntityModel<UserCreatedDto> entityModel = assemblerManager.getAssembler(UserCreatedDto.class)
                .toModel(userCreatedDto, trainee.getUser().getUsername(), HttpMethod.POST, "trainee");

        return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<EntityModel<TraineeProfileDto>> getTraineeProfile(@PathVariable String username) {
        try {
            Trainee trainee = service.getByUsername(username);
            List<Trainer> trainers = trainingService.getTrainers(trainee.getId());
            TraineeProfileDto profileDto = traineeMapper.toTraineeProfileDto(trainee, trainers);

            EntityModel<TraineeProfileDto> entityModel = assemblerManager.getAssembler(TraineeProfileDto.class)
                    .toModel(profileDto, username, HttpMethod.GET, "trainee");

            return new ResponseEntity<>(entityModel, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{username}/trainings")
    public ResponseEntity<CollectionModel<EntityModel<TraineeTrainingDto>>> getTrainings(@PathVariable String username,
                                                                                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date periodFrom,
                                                                                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date periodTo,
                                                                                         @RequestParam(required = false) String trainerName,
                                                                                         @RequestParam(required = false) Long trainingType) {
        List<Training> trainings = trainingService.getTraineeTrainings(username, periodFrom, periodTo, trainerName, trainingType);
        List<TraineeTrainingDto> trainingsList = trainingMapper.troTraineeTrainings(trainings);

        TraineeTrainingsParams params = new TraineeTrainingsParams(periodFrom, periodTo, trainerName, trainingType);
        var entityModel = assemblerManager.getAssembler(TraineeTrainingDto.class)
                .toCollectionModel(trainingsList, username, params, HttpMethod.GET, "trainee");

        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }

    @PutMapping("/{username}")
    public ResponseEntity<EntityModel<TraineeProfileDto>> updateTraineeProfile(@PathVariable String username, @RequestBody @Valid UpdateTraineeDto traineeDto) {
        try {
            Trainee trainee = traineeService.update(username, traineeDto);
            List<Trainer> trainers = trainingService.getTrainers(trainee.getId());
            TraineeProfileDto profile = traineeMapper.toTraineeProfileDto(trainee, trainers);

            EntityModel<TraineeProfileDto> entityModel = assemblerManager.getAssembler(TraineeProfileDto.class)
                    .toModel(profile, username, HttpMethod.PUT, "trainee");

            return new ResponseEntity<>(entityModel, HttpStatus.OK);

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
