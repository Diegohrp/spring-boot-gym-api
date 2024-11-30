package com.diegohrp.gymapi.service;

import com.diegohrp.gymapi.aspects.LoggableTransaction;
import com.diegohrp.gymapi.dto.trainee.CreateTraineeDto;
import com.diegohrp.gymapi.dto.trainee.UpdateTraineeDto;
import com.diegohrp.gymapi.entity.user.Trainee;
import com.diegohrp.gymapi.entity.user.User;
import com.diegohrp.gymapi.mapper.TraineeMapper;
import com.diegohrp.gymapi.repository.TraineeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TraineeService {
    private final TraineeRepository repository;
    private final UserService userService;
    private final TraineeMapper traineeMapper;

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
    public Trainee getByUsername(String username) {
        Optional<Trainee> trainee = repository.findByUsername(username);
        if (trainee.isEmpty()) {
            throw new EntityNotFoundException("Trainee not found");
        }
        return trainee.get();
    }

    @Transactional
    @LoggableTransaction
    public Trainee update(String username, UpdateTraineeDto traineeDto) {
        Trainee trainee = this.getByUsername(username);
        User user = trainee.getUser();
        userService.update(user, traineeDto);
        traineeMapper.updateTraineeFromDto(traineeDto, trainee);
        repository.save(trainee);
        return trainee;
    }
}
