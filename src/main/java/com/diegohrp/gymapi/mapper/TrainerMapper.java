package com.diegohrp.gymapi.mapper;

import com.diegohrp.gymapi.dto.trainee.TraineeProfileDto;
import com.diegohrp.gymapi.dto.trainer.TrainerProfileDto;
import com.diegohrp.gymapi.dto.trainer.TrainerSummaryDto;
import com.diegohrp.gymapi.entity.user.Trainee;
import com.diegohrp.gymapi.entity.user.Trainer;
import com.diegohrp.gymapi.mapper.annotation.MapUserAtributes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = SummaryMapper.class)
public interface TrainerMapper {

    @MapUserAtributes
    @Mapping(source = "entity.user.isActive", target = "isActive")
    @Mapping(source = "entity.speciality.name", target = "speciality")
    @Mapping(source = "trainees", target = "traineesList")
    TrainerProfileDto toTrainerProfileDto(Trainer entity, List<Trainee> trainees);
}
