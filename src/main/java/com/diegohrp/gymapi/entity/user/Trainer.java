package com.diegohrp.gymapi.entity.user;

import com.diegohrp.gymapi.entity.training.Training;
import com.diegohrp.gymapi.entity.training.TrainingType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "trainers")
@NoArgsConstructor
@Getter
@Setter
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "speciality")
    private TrainingType speciality;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @OneToMany(mappedBy = "trainer")
    private List<Training> trainings;
}
