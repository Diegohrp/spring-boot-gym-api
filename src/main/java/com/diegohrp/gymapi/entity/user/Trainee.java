package com.diegohrp.gymapi.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "trainees")
@NoArgsConstructor
@Setter
@Getter
public class Trainee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column
    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;
}
