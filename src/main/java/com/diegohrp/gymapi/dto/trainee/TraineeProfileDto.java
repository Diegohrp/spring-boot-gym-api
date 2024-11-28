package com.diegohrp.gymapi.dto.trainee;

import java.util.Date;

public record TraineeProfileDto(Long id, String firstName, String lastName, String username, Boolean isActive,
                                String address, Date dateOfBirth) {
}
