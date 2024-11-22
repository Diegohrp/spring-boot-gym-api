package com.diegohrp.gymapi.service;

import com.diegohrp.gymapi.dto.trainee.CreateTraineeDto;
import com.diegohrp.gymapi.entity.user.Trainee;
import com.diegohrp.gymapi.entity.user.User;
import com.diegohrp.gymapi.repository.TraineeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TraineeService {
    private final TraineeRepository repository;
    private final UserService userService;

    @Transactional
    public Trainee create(CreateTraineeDto traineeDto) {
        //logger.info("-------------------------------------------------- Trainee Creation --------------------------------------------------");
        User user = userService.create(traineeDto.user().firstName(), traineeDto.user().lastName());
        Trainee trainee = new Trainee(traineeDto.dateOfBirth(), traineeDto.address());
        trainee.setUser(user);
        repository.save(trainee);
        //logger.info("New Trainee created: {}", trainee);
        return trainee;
    }
}