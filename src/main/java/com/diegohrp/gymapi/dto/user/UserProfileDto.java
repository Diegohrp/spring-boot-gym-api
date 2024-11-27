package com.diegohrp.gymapi.dto.user;

public record UserProfileDto(Long id, String firstName, String lastName, String username, Boolean isActive) {
}
