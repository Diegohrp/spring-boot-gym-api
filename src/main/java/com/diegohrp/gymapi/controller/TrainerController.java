package com.diegohrp.gymapi.controller;

import com.diegohrp.gymapi.dto.trainer.CreateTrainerDto;
import com.diegohrp.gymapi.dto.user.UserCreatedDto;
import com.diegohrp.gymapi.entity.user.Trainer;
import com.diegohrp.gymapi.mapper.UserMapper;
import com.diegohrp.gymapi.service.TrainerService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/trainers")
@RequiredArgsConstructor
public class TrainerController {
    private final TrainerService service;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserCreatedDto> create(@RequestBody @Valid CreateTrainerDto dto) {
        try {
            Trainer trainer = service.create(dto);
            return new ResponseEntity<>(userMapper.toCreatedUserDto(trainer.getUser()), HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
