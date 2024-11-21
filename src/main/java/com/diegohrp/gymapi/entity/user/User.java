package com.diegohrp.gymapi.entity.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @OneToOne(mappedBy = "user")
    private Trainee trainee;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
