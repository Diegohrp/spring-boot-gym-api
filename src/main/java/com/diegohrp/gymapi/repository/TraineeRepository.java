package com.diegohrp.gymapi.repository;

import com.diegohrp.gymapi.entity.user.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TraineeRepository extends JpaRepository<Trainee, Long> {
    @Query(value = "SELECT t FROM Trainee t WHERE t.user.username = :username")
    Optional<Trainee> findByUsername(String username);
}
