package com.diegohrp.gymapi.dto.trainee;

import com.diegohrp.gymapi.dto.user.UserProfileDto;

import java.util.Date;

public record TraineeProfileDto(Long id, String firstName, String lastName, String username, Boolean isActive,
                                String address, Date dateOfBirth) {
}
