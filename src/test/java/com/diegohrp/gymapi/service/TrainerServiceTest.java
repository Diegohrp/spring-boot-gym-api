package com.diegohrp.gymapi.service;

import com.diegohrp.gymapi.dto.trainer.CreateTrainerDto;
import com.diegohrp.gymapi.dto.trainer.UpdateTrainerDto;
import com.diegohrp.gymapi.dto.user.CreateUserDto;
import com.diegohrp.gymapi.entity.training.TrainingType;
import com.diegohrp.gymapi.entity.user.Trainer;
import com.diegohrp.gymapi.entity.user.User;
import com.diegohrp.gymapi.repository.TrainerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TrainerServiceTest {
    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private UserService userService;
    @Mock
    private TrainingTypeService trainingTypeService;

    @InjectMocks
    private TrainerService trainerService;
    private CreateTrainerDto trainerDto;

    @BeforeEach
    void setUp() {
        CreateUserDto userDto = new CreateUserDto("John", "Doe");
        trainerDto = new CreateTrainerDto(userDto, 1L);
    }

    @Test
    void testCreate() {
        when(trainingTypeService.getById(anyLong())).thenReturn(new TrainingType());
        when(userService.create(anyString(), anyString())).thenReturn(new User());
        when(trainerRepository.save(any(Trainer.class))).thenReturn(new Trainer());
        Trainer trainer = trainerService.create(trainerDto);
        verify(trainingTypeService, times(1)).getById(anyLong());
        verify(userService, times(1)).create(anyString(), anyString());
        verify(trainerRepository, times(1)).save(any(Trainer.class));
    }

    @Test
    void testGetByUsername() {
        Trainer trainer = new Trainer();
        when(trainerRepository.findByUsername(anyString())).thenReturn(Optional.of(trainer));
        Trainer result = trainerService.getByUsername("johndoe");
        verify(trainerRepository, times(1)).findByUsername(anyString());
        assertNotNull(result);
    }

    @Test
    void testUpdate() {
        Trainer trainer = new Trainer();
        trainer.setUser(new User("John", "Doe"));
        trainer.setSpeciality(new TrainingType());
        UpdateTrainerDto updateDto = new UpdateTrainerDto("Jane", "Doe", true);
        when(trainerRepository.findByUsername(anyString())).thenReturn(Optional.of(trainer));
        trainerService.update("johndoe", updateDto);
        verify(trainerRepository, times(1)).findByUsername(anyString());
        verify(trainerRepository, times(1)).save(any(Trainer.class));
    }

    @Test
    void testGetUnassigned() {
        List<Trainer> trainers = new ArrayList<>();
        Trainer trainer = new Trainer();
        trainer.setUser(new User("John", "Doe"));
        trainer.setSpeciality(new TrainingType());
        trainers.add(trainer);
        when(trainerRepository.getUnassigned(anyString())).thenReturn(trainers);
        List<Trainer> result = trainerService.getUnassigned("trainee");
        verify(trainerRepository, times(1)).getUnassigned(anyString());

        assertEquals(1, result.size());
    }
}