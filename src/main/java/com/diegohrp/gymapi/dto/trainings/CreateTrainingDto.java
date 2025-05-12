package com.diegohrp.gymapi.dto.trainings;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Date;

public record CreateTrainingDto(
        @NotBlank String traineeUser,
        @NotBlank String trainerUser,
        @NotBlank String name,
        @NotNull LocalDate date,
        @Min(30) @Max(120) Integer duration
) {
}

