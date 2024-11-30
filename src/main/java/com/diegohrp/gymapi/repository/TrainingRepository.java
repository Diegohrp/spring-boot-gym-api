package com.diegohrp.gymapi.repository;

import com.diegohrp.gymapi.entity.training.Training;
import com.diegohrp.gymapi.entity.user.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TrainingRepository extends JpaRepository<Training, Long> {
    @Query("SELECT t FROM Training training " +
            "JOIN training.trainer t " +
            "JOIN FETCH t.user " +
            "JOIN FETCH t.speciality " +
            "WHERE training.trainee.id = :traineeId")
    List<Trainer> getTrainersFromTrainings(Long traineeId);
}
