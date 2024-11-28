package com.diegohrp.gymapi.dto.trainer;

import com.diegohrp.gymapi.dto.user.CreateUserDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CreateTrainerDto(@Valid @NotNull CreateUserDto user) {
}
