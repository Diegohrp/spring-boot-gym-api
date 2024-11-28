package com.diegohrp.gymapi.mapper;

import com.diegohrp.gymapi.dto.user.CreateUserDto;
import com.diegohrp.gymapi.dto.user.UserCreatedDto;
import com.diegohrp.gymapi.entity.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserCreatedDto toCreatedUserDto(User user);
}
