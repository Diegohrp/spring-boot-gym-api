package com.diegohrp.gymapi.repository;

import com.diegohrp.gymapi.entity.user.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    @Query("SELECT t FROM Trainer t JOIN FETCH t.user WHERE t.user.username = :username")
    Optional<Trainer> findByUsername(String username);

    @Query("SELECT t FROM Trainer t JOIN FETCH t.user JOIN FETCH t.speciality " +
            "WHERE t NOT IN (SELECT t2 FROM Trainer  t2 INNER JOIN t2.trainings trainings " +
            "WHERE trainings.trainee.user.username =:trainee) " +
            "AND t.user.isActive=TRUE")
    List<Trainer> getUnassigned(String trainee);
}
