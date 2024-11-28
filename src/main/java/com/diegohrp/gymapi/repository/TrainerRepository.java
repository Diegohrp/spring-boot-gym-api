package com.diegohrp.gymapi.repository;

import com.diegohrp.gymapi.entity.user.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    @Query("SELECT t FROM Trainer t WHERE t.user.username = :username")
    Optional<Trainer> findByUsername(String username);
}
