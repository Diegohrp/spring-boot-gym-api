package com.diegohrp.gymapi.dto.user;

public record UpdateUserDto(String firstName, String lastName, Boolean isActive, String newPassword) {
}
