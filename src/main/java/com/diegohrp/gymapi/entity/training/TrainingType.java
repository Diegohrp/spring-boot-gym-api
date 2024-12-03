package com.diegohrp.gymapi.entity.training;

import com.diegohrp.gymapi.entity.user.Trainer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "training_types")
@NoArgsConstructor
@Setter
@Getter
public class TrainingType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;

    @OneToMany(mappedBy = "speciality", fetch = FetchType.LAZY)
    private List<Trainer> trainers;

    @OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
    private List<Training> trainings;
}
