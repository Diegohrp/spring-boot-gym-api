package com.diegohrp.gymapi.dto.user;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordDto(@NotBlank String username, @NotBlank String oldPassword, @NotBlank String newPassword) {
}
