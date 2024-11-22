package com.diegohrp.gymapi.controller;

import com.diegohrp.gymapi.dto.trainee.CreateTraineeDto;
import com.diegohrp.gymapi.dto.user.UserCreatedDto;
import com.diegohrp.gymapi.entity.user.Trainee;
import com.diegohrp.gymapi.mapper.UserMapper;
import com.diegohrp.gymapi.service.TraineeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trainees")
@RequiredArgsConstructor
public class TraineeController {
    private final TraineeService service;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("HELLO", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserCreatedDto> create(@RequestBody @Valid CreateTraineeDto traineeDto) {
        Trainee trainee = service.create(traineeDto);
        return new ResponseEntity<>(userMapper.toCreateUserDto(trainee.getUser()), HttpStatus.CREATED);
    }
}
