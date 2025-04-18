package com.diegohrp.gymapi.entity.training;

import com.diegohrp.gymapi.entity.user.Trainee;
import com.diegohrp.gymapi.entity.user.Trainer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "trainings")
@NoArgsConstructor
@Setter
@Getter
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainee_id")
    private Trainee trainee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private TrainingType type;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private Integer duration;

    public Training(Trainee trainee, Trainer trainer, TrainingType type, String name, LocalDate date, Integer duration) {
        this.trainee = trainee;
        this.trainer = trainer;
        this.type = type;
        this.name = name;
        this.date = date;
        this.duration = duration;
    }
}
