package com.diegohrp.gymapi.service;

import com.diegohrp.gymapi.aspects.LoggableTransaction;
import com.diegohrp.gymapi.dto.trainer.CreateTrainerDto;
import com.diegohrp.gymapi.entity.user.Trainer;
import com.diegohrp.gymapi.entity.user.User;
import com.diegohrp.gymapi.repository.TrainerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainerService {
    private final UserService userService;
    private final TrainingTypeService trainingTypeService;
    private final TrainerRepository repository;

    @Transactional
    @LoggableTransaction
    public Trainer create(CreateTrainerDto trainerDto) {
        Trainer trainer = new Trainer();
        try {
            trainer.setSpeciality(trainingTypeService.getById(trainerDto.speciality()));
            User user = userService.create(trainerDto.user().firstName(), trainerDto.user().lastName());
            trainer.setUser(user);
            repository.save(trainer);
            return trainer;
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Error trying to create Trainer: " + e.getMessage());
        }
    }

    @Transactional
    @LoggableTransaction
    public Trainer getByUsername(String username) {
        Optional<Trainer> trainer = repository.findByUsername(username);
        if (trainer.isEmpty()) {
            throw new EntityNotFoundException("Trainer not found");
        }
        return trainer.get();
    }
}
