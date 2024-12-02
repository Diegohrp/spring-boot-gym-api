package com.diegohrp.gymapi.service;

import com.diegohrp.gymapi.aspects.LoggableTransaction;
import com.diegohrp.gymapi.entity.training.Training;
import com.diegohrp.gymapi.entity.user.Trainee;
import com.diegohrp.gymapi.entity.user.Trainer;
import com.diegohrp.gymapi.repository.TrainingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TrainingService {
    private final TrainingRepository repository;

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
}
