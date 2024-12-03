package com.diegohrp.gymapi.dto.trainer;

import com.diegohrp.gymapi.dto.trainee.TraineeSummaryDto;

import java.util.List;

public record TrainerProfileDto(Long id, String firstName, String lastName, String username, String speciality,
                                Boolean isActive,
                                List<TraineeSummaryDto> traineesList) {
}
