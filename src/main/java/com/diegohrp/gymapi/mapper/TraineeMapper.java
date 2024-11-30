package com.diegohrp.gymapi.mapper;

import com.diegohrp.gymapi.dto.trainee.TraineeProfileDto;
import com.diegohrp.gymapi.dto.trainee.UpdateTraineeDto;
import com.diegohrp.gymapi.entity.user.Trainee;
import com.diegohrp.gymapi.entity.user.Trainer;
import com.diegohrp.gymapi.mapper.annotation.MapUserAtributes;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = TrainerMapper.class)
public interface TraineeMapper {

    @MapUserAtributes
    @Mapping(source = "entity.user.isActive", target = "isActive")
    @Mapping(source = "trainers", target = "trainersList")
    TraineeProfileDto toTraineeProfileDto(Trainee entity, List<Trainer> trainers);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTraineeFromDto(UpdateTraineeDto dto, @MappingTarget Trainee trainee);
}
