package com.diegohrp.gymapi.service;

import com.diegohrp.gymapi.dto.trainee.CreateTraineeDto;
import com.diegohrp.gymapi.dto.trainee.UpdateTraineeDto;
import com.diegohrp.gymapi.dto.user.CreateUserDto;
import com.diegohrp.gymapi.entity.user.Trainee;
import com.diegohrp.gymapi.entity.user.User;
import com.diegohrp.gymapi.mapper.TraineeMapper;
import com.diegohrp.gymapi.repository.TraineeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TraineeServiceTest {
    @Mock
    private TraineeRepository traineeRepository;
    @Mock
    private UserService userService;
    @Mock
    private TraineeMapper mapper;

    @InjectMocks
    private TraineeService traineeService;
    private CreateTraineeDto traineeDto;

    @BeforeEach
    void setUp() {
        CreateUserDto userDto = new CreateUserDto("John", "Doe");
        traineeDto = new CreateTraineeDto(userDto, new Date(), "123 Main St");
    }

    @Test
    void testCreate() {
        when(userService.create(anyString(), anyString())).thenReturn(new User());
        when(traineeRepository.save(any(Trainee.class))).thenReturn(new Trainee());
        Trainee trainee = traineeService.create(traineeDto);
        verify(userService, times(1)).create(anyString(), anyString());
        verify(traineeRepository, times(1)).save(any(Trainee.class));
    }

    @Test
    void testGetByUsername() {
        Trainee trainee = new Trainee(new Date(), "123 Main St");
        when(traineeRepository.findByUsername(anyString())).thenReturn(Optional.of(trainee));
        Trainee result = traineeService.getByUsername("johndoe");
        verify(traineeRepository, times(1)).findByUsername(anyString());
        assertNotNull(result);
    }

    @Test
    void testUpdate() {
        Trainee trainee = new Trainee(new Date(), "123 Main St");
        trainee.setUser(new User("John", "Doe"));
        UpdateTraineeDto updateDto = new UpdateTraineeDto("Jane", "Doe", new Date(), "456 Elm St", true);
        when(traineeRepository.findByUsername(anyString())).thenReturn(Optional.of(trainee));
        traineeService.update("John.Doe", updateDto);
        verify(traineeRepository, times(1)).findByUsername(anyString());
        verify(traineeRepository, times(1)).save(any(Trainee.class));
    }

    @Test
    void testDelete() {
        Trainee trainee = new Trainee(new Date(), "123 Main St");
        when(traineeRepository.findByUsername(anyString())).thenReturn(Optional.of(trainee));
        doNothing().when(traineeRepository).delete(any(Trainee.class));
        traineeService.delete("johndoe");
        verify(traineeRepository, times(1)).findByUsername(anyString());
        verify(traineeRepository, times(1)).delete(any(Trainee.class));
    }
}