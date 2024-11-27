package com.diegohrp.gymapi.mapper;

import com.diegohrp.gymapi.dto.trainee.TraineeProfileDto;
import com.diegohrp.gymapi.entity.user.Trainee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")

public interface TraineeMapper {
    @Mappings({
            @Mapping(source = "user.id", target = "id"),
            @Mapping(source = "user.username", target = "username"),
            @Mapping(source = "user.firstName", target = "firstName"),
            @Mapping(source = "user.lastName", target = "lastName"),
            @Mapping(source = "user.isActive", target = "isActive")
    })
    TraineeProfileDto toTraineeProfileDto(Trainee trainee);
}
