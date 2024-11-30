package com.diegohrp.gymapi.dto.trainee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record UpdateTraineeDto(@NotBlank String firstName, @NotBlank String lastName, Date dateOfBirth, String address,
                               @NotNull Boolean isActive) {
}
