package com.diegohrp.gymapi.service;

import com.diegohrp.gymapi.dto.trainings.CreateTrainingDto;
import com.diegohrp.gymapi.entity.training.Training;
import com.diegohrp.gymapi.entity.training.TrainingType;
import com.diegohrp.gymapi.entity.user.Trainee;
import com.diegohrp.gymapi.entity.user.Trainer;
import com.diegohrp.gymapi.entity.user.User;
import com.diegohrp.gymapi.repository.TrainingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TrainingServiceTest {
    @Mock
    private TrainingRepository trainingRepository;
    @Mock
    private TraineeService traineeService;
    @Mock
    private TrainerService trainerService;

    @InjectMocks
    private TrainingService trainingService;

    private CreateTrainingDto trainingDto;
    private Training training;

    @BeforeEach
    void setUp() {
        trainingDto = new CreateTrainingDto("trainee", "trainer", "Cardio Session", new Date(), 60);
        Trainer trainer = new Trainer();
        trainer.setUser(new User("John", "Doe"));
        trainer.setSpeciality(new TrainingType());
        Trainee trainee = new Trainee(new Date(), "123 Main St");
        trainee.setUser(new User("Jane", "Doe"));
        training = new Training(trainee, trainer, new TrainingType(), "Some training", new Date(), 40);
    }

    @Test
    void testCreate() {
        Trainee trainee = new Trainee();
        Trainer trainer = new Trainer();
        when(traineeService.getByUsername(anyString())).thenReturn(trainee);
        when(trainerService.getByUsername(anyString())).thenReturn(trainer);
        when(trainingRepository.save(any(Training.class))).thenReturn(training);
        trainingService.create(trainingDto);
        verify(traineeService, times(1)).getByUsername(anyString());
        verify(trainerService, times(1)).getByUsername(anyString());
        verify(trainingRepository, times(1)).save(any(Training.class));
    }


    @Test
    void testGetTraineeTrainings() {
        List<Training> trainings = List.of(training);
        when(trainingRepository.findTraineeTrainings(anyString(), any(Date.class), any(Date.class), anyString(), anyLong())).thenReturn(trainings);
        List<Training> result = trainingService.getTraineeTrainings("trainee", new Date(), new Date(), "trainerName", 1L);
        verify(trainingRepository, times(1)).findTraineeTrainings(anyString(), any(Date.class), any(Date.class), anyString(), anyLong());
        assertEquals(1, result.size());
    }

    @Test
    void testGetTrainerTrainings() {
        List<Training> trainings = List.of(training);
        when(trainingRepository.findTrainerTrainings(anyString(), any(Date.class), any(Date.class), anyString())).thenReturn(trainings);
        List<Training> result = trainingService.getTrainerTrainings("trainer", new Date(), new Date(), "traineeName");
        verify(trainingRepository, times(1)).findTrainerTrainings(anyString(), any(Date.class), any(Date.class), anyString());

        assertEquals(1, result.size());
    }
}