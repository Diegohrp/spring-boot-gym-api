package com.diegohrp.gymapi.service;

import com.diegohrp.gymapi.aspects.LoggableTransaction;
import com.diegohrp.gymapi.entity.training.TrainingType;
import com.diegohrp.gymapi.repository.TrainingTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainingTypeService {
    private final TrainingTypeRepository repository;

    @Transactional
    @LoggableTransaction
    public TrainingType getById(Long id) {
        Optional<TrainingType> trainingType = repository.findById(id);
        if (trainingType.isEmpty()) {
            throw new EntityNotFoundException("This training type does not exist");
        }
        return trainingType.get();
    }
}
