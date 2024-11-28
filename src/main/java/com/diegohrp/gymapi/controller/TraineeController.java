package com.diegohrp.gymapi.controller;

import com.diegohrp.gymapi.dto.trainee.CreateTraineeDto;
import com.diegohrp.gymapi.dto.trainee.TraineeProfileDto;
import com.diegohrp.gymapi.dto.user.UserCreatedDto;
import com.diegohrp.gymapi.entity.user.Trainee;
import com.diegohrp.gymapi.mapper.TraineeMapper;
import com.diegohrp.gymapi.mapper.UserMapper;
import com.diegohrp.gymapi.service.TraineeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@RestController
@RequestMapping("/trainees")
@RequiredArgsConstructor
public class TraineeController {
    private final TraineeService service;
    private final UserMapper userMapper;
    private final TraineeMapper traineeMapper;

    @GetMapping
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("HELLO", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserCreatedDto> create(@RequestBody @Valid CreateTraineeDto traineeDto) {
        Trainee trainee = service.create(traineeDto);
        return new ResponseEntity<>(userMapper.toCreateUserDto(trainee.getUser()), HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<TraineeProfileDto> getTraineeProfile(@PathVariable String username) {
        Optional<Trainee> trainee = service.getByUsername(username);
        if (trainee.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Trainee not found");
        }
        return new ResponseEntity<>(traineeMapper.toTraineeProfileDto(trainee.get()), HttpStatus.OK);
    }
}
