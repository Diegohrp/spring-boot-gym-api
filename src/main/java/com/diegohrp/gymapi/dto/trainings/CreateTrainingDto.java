package com.diegohrp.gymapi.dto.trainings;

import jakarta.validation.constraints.*;

import java.util.Date;

public record CreateTrainingDto(
        @NotBlank String traineeUser,
        @NotBlank String trainerUser,
        @NotBlank String name,
        @NotNull Date date,
        @Min(30) @Max(120) Integer duration
) {
}

