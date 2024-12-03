package com.diegohrp.gymapi.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UpdateStatusDto(@NotEmpty String username, @NotNull Boolean isActive) {
}
