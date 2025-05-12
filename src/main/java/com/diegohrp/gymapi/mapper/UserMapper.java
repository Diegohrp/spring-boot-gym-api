package com.diegohrp.gymapi.mapper;

import com.diegohrp.gymapi.dto.trainee.UpdateTraineeDto;
import com.diegohrp.gymapi.dto.trainer.UpdateTrainerDto;
import com.diegohrp.gymapi.dto.user.UserCreatedDto;
import com.diegohrp.gymapi.entity.user.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "user.plainGeneratedPassword", target = "password")
    UserCreatedDto toCreatedUserDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UpdateTraineeDto dto, @MappingTarget User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UpdateTrainerDto dto, @MappingTarget User user);
}
