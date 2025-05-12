package com.diegohrp.gymapi.repository;

import com.diegohrp.gymapi.entity.training.Training;
import com.diegohrp.gymapi.entity.training.TrainingType;
import com.diegohrp.gymapi.entity.user.Trainee;
import com.diegohrp.gymapi.entity.user.Trainer;
import com.diegohrp.gymapi.entity.user.User;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;


public interface TrainingRepository extends JpaRepository<Training, Long>, JpaSpecificationExecutor<Training> {
    @Query("SELECT DISTINCT t FROM Training training " +
            "JOIN training.trainer t " +
            "JOIN FETCH t.user " +
            "JOIN FETCH t.speciality " +
            "WHERE training.trainee.id = :traineeId")
    List<Trainer> getTrainersFromTrainings(Long traineeId);

    @Query("SELECT DISTINCT t FROM Training training " +
            "JOIN training.trainee t " +
            "JOIN FETCH t.user " +
            "WHERE training.trainer.id = :trainerId")
    List<Trainee> getTraineesFromTrainings(Long trainerId);

    @Query("SELECT SUM(t.duration) FROM Training t " +
            "WHERE t.trainer.id = :trainerId " +
            "AND t.date >= :startDate " +
            "AND t.date < :endDate")
    Integer getTrainingHours(Long trainerId, LocalDate startDate, LocalDate endDate);


    default List<Training> findTraineeTrainings(
            String username,
            Date periodFrom,
            Date periodTo,
            String trainerName,
            Long trainingTypeId
    ) {
        Specification<Training> spec = Specification.where(byUsername(username))
                .and(periodFrom != null ? byPeriodFrom(periodFrom) : null)
                .and(periodTo != null ? byPeriodTo(periodTo) : null)
                .and(trainerName != null ? byTrainerName(trainerName) : null)
                .and(trainingTypeId != null ? byTrainingType(trainingTypeId) : null);

        return findAll(spec);
    }

    // Individual specifications
    static Specification<Training> byUsername(String username) {
        return (root, query, criteriaBuilder) -> {
            Join<Training, Trainee> traineeJoin = root.join("trainee");
            Join<Trainee, User> userJoin = traineeJoin.join("user");
            return criteriaBuilder.equal(userJoin.get("username"), username);
        };
    }

    static Specification<Training> byPeriodFrom(Date periodFrom) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("date"), periodFrom);
    }

    static Specification<Training> byPeriodTo(Date periodTo) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("date"), periodTo);
    }

    static Specification<Training> byTrainerName(String trainerName) {
        return (root, query, criteriaBuilder) -> {
            Join<Training, Trainer> trainerJoin = root.join("trainer");
            Join<Trainer, User> userJoin = trainerJoin.join("user");
            return criteriaBuilder.equal(
                    criteriaBuilder.lower(userJoin.get("firstName")),
                    trainerName.toLowerCase()
            );
        };
    }

    static Specification<Training> byTrainingType(Long trainingTypeId) {
        return (root, query, criteriaBuilder) -> {
            Join<Training, TrainingType> typeJoin = root.join("type");
            return criteriaBuilder.equal(typeJoin.get("id"), trainingTypeId);
        };
    }


    default List<Training> findTrainerTrainings(
            String username,
            Date periodFrom,
            Date periodTo,
            String traineeName
    ) {
        Specification<Training> spec = Specification.where(byTrainerUsername(username))
                .and(periodFrom != null ? byPeriodFrom(periodFrom) : null)
                .and(periodTo != null ? byPeriodTo(periodTo) : null)
                .and(traineeName != null ? byTraineeName(traineeName) : null);

        return findAll(spec);
    }

    // Individual specifications
    static Specification<Training> byTrainerUsername(String username) {
        return (root, query, criteriaBuilder) -> {
            Join<Training, Trainer> traineeJoin = root.join("trainer");
            Join<Trainee, User> userJoin = traineeJoin.join("user");
            return criteriaBuilder.equal(userJoin.get("username"), username);
        };
    }


    static Specification<Training> byTraineeName(String trainerName) {
        return (root, query, criteriaBuilder) -> {
            Join<Training, Trainee> traineeJoin = root.join("trainee");
            Join<Trainee, User> userJoin = traineeJoin.join("user");
            return criteriaBuilder.equal(
                    criteriaBuilder.lower(userJoin.get("firstName")),
                    trainerName.toLowerCase()
            );
        };
    }
}
