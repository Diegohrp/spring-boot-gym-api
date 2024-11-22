package com.diegohrp.gymapi.dto.trainee;

import com.diegohrp.gymapi.dto.user.CreateUserDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record CreateTraineeDto(@Valid @NotNull CreateUserDto user, Date dateOfBirth, String address) {
}
