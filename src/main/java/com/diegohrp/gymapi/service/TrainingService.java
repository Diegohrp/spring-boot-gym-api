package com.diegohrp.gymapi.service;

import com.diegohrp.gymapi.aspects.LoggableTransaction;
import com.diegohrp.gymapi.dto.trainer.TrainerWorkloadDto;
import com.diegohrp.gymapi.dto.trainings.CreateTrainingDto;
import com.diegohrp.gymapi.entity.training.Training;
import com.diegohrp.gymapi.entity.user.Trainee;
import com.diegohrp.gymapi.entity.user.Trainer;
import com.diegohrp.gymapi.enums.ActionTypes;
import com.diegohrp.gymapi.mapper.TrainerMapper;
import com.diegohrp.gymapi.repository.TrainingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TrainingService {
    private final TrainingRepository repository;
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainerMapper trainerMapper;
    private final RestTemplate restTemplate;

    @Transactional
    @LoggableTransaction
    public Training create(CreateTrainingDto trainingDto) {
        Trainee trainee = traineeService.getByUsername(trainingDto.traineeUser());
        Trainer trainer = trainerService.getByUsername(trainingDto.trainerUser());
        Training training = new Training(
                trainee,
                trainer,
                trainer.getSpeciality(),
                trainingDto.name(),
                trainingDto.date(),
                trainingDto.duration());
        repository.save(training);

        TrainerWorkloadDto workload = trainerMapper.toTrainerWorkloadDto(trainer, training, ActionTypes.ADD);
        restTemplate.postForEntity(
                "http://training-hours-service/training-hours/api/v1/trainers/training-hours",
                workload,
                Void.class
        );

        return training;
    }

    @Transactional
    @LoggableTransaction
    public List<Trainer> getTrainers(Long traineeId) {
        return repository.getTrainersFromTrainings(traineeId);
    }

    @Transactional
    @LoggableTransaction
    public List<Trainee> getTrainees(Long trainerId) {
        return repository.getTraineesFromTrainings(trainerId);
    }

    @Transactional
    @LoggableTransaction
    public List<Training> getTraineeTrainings(String username, Date periodFrom, Date periodTo, String trainerName, Long trainingType) {
        return repository.findTraineeTrainings(username, periodFrom, periodTo, trainerName, trainingType);
    }

    @Transactional
    @LoggableTransaction
    public List<Training> getTrainerTrainings(String username, Date periodFrom, Date periodTo, String traineeName) {
        return repository.findTrainerTrainings(username, periodFrom, periodTo, traineeName);
    }
}
