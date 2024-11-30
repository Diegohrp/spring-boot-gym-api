package com.diegohrp.gymapi.entity.training;

import com.diegohrp.gymapi.entity.user.Trainee;
import com.diegohrp.gymapi.entity.user.Trainer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "trainings")
@NoArgsConstructor
@Setter
@Getter
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trainee_id")
    private Trainee trainee;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private TrainingType type;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Date date;
    @Column(nullable = false)
    private Integer duration;
}
