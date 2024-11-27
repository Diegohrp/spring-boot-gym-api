package com.diegohrp.gymapi.service;

import com.diegohrp.gymapi.aspects.LoggableTransaction;
import com.diegohrp.gymapi.dto.trainee.CreateTraineeDto;
import com.diegohrp.gymapi.entity.user.Trainee;
import com.diegohrp.gymapi.entity.user.User;
import com.diegohrp.gymapi.repository.TraineeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TraineeService {
    private final TraineeRepository repository;
    private final UserService userService;

    @Transactional
    @LoggableTransaction
    public Trainee create(CreateTraineeDto traineeDto) {
        User user = userService.create(traineeDto.user().firstName(), traineeDto.user().lastName());
        Trainee trainee = new Trainee(traineeDto.dateOfBirth(), traineeDto.address());
        trainee.setUser(user);
        repository.save(trainee);
        return trainee;
    }

    @Transactional
    public Optional<Trainee> getByUsername(String username) {
        return repository.findByUsername(username);
    }
}
