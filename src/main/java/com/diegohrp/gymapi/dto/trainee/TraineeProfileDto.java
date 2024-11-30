package com.diegohrp.gymapi.dto.trainee;

import com.diegohrp.gymapi.dto.trainer.TrainerSummaryDto;

import java.util.Date;
import java.util.List;

public record TraineeProfileDto(Long id, String firstName, String lastName, String username, Boolean isActive,
                                String address, Date dateOfBirth, List<TrainerSummaryDto> trainersList) {
}
