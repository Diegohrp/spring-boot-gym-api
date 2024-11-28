package com.diegohrp.gymapi.dto.user;

import jakarta.validation.constraints.NotBlank;

public record LoginUserDto(@NotBlank String username, @NotBlank String password) {
}
