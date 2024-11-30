package com.diegohrp.gymapi.service;

import com.diegohrp.gymapi.entity.user.Trainer;
import com.diegohrp.gymapi.repository.TrainingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TrainingService {
    private final TrainingRepository repository;

    public List<Trainer> getTrainers(Long traineeId) {
        return repository.getTrainersFromTrainings(traineeId);
    }
}
