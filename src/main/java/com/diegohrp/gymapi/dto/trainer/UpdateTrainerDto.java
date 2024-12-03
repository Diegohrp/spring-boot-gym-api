package com.diegohrp.gymapi.dto.trainer;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record UpdateTrainerDto(@NotBlank String firstName, @NotBlank String lastName, @NotNull Boolean isActive) {
}
