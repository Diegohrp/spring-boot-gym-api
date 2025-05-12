package com.diegohrp.gymapi.dto.trainer;

import com.diegohrp.gymapi.enums.ActionTypes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TrainerWorkloadDto(
        String username,
        String firstName,
        String lastName,
        Boolean isActive,
        LocalDate date,
        Integer duration,
        Integer currentWorkload,
        ActionTypes actionType) {
}
