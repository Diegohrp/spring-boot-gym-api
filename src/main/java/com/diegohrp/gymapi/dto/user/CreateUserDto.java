package com.diegohrp.gymapi.dto.user;

import jakarta.validation.constraints.NotBlank;

public record CreateUserDto(@NotBlank String firstName, @NotBlank String lastName) {
}
