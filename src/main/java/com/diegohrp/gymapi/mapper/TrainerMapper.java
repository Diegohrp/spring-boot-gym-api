package com.diegohrp.gymapi.mapper;

import com.diegohrp.gymapi.dto.trainer.TrainerSummaryDto;
import com.diegohrp.gymapi.entity.user.Trainer;
import com.diegohrp.gymapi.mapper.annotation.MapUserAtributes;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainerMapper {

    @MapUserAtributes
    TrainerSummaryDto toTrainerSummaryDto(Trainer entity);

    List<TrainerSummaryDto> toTrainersList(List<Trainer> trainers);
}
