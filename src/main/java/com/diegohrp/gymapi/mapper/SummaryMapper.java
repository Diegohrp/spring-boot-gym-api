package com.diegohrp.gymapi.mapper;

import com.diegohrp.gymapi.dto.trainee.TraineeSummaryDto;
import com.diegohrp.gymapi.dto.trainer.TrainerSummaryDto;
import com.diegohrp.gymapi.entity.user.Trainee;
import com.diegohrp.gymapi.entity.user.Trainer;
import com.diegohrp.gymapi.mapper.annotation.MapUserAtributes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SummaryMapper {
    @MapUserAtributes
    @Mapping(source = "entity.speciality.name", target = "speciality")
    TrainerSummaryDto toTrainerSummaryDto(Trainer entity);
    List<TrainerSummaryDto> toTrainersList(List<Trainer> trainers);

    @MapUserAtributes
    TraineeSummaryDto toTraineeSummaryDto(Trainee entity);
    List<TraineeSummaryDto> toTraineesList(List<Trainee> trainees);
}
