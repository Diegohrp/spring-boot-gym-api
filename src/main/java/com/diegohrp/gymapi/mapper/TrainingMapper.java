package com.diegohrp.gymapi.mapper;

import com.diegohrp.gymapi.dto.trainings.TraineeTrainingDto;
import com.diegohrp.gymapi.entity.training.Training;
import com.diegohrp.gymapi.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainingMapper {

    @Mapping(source = "type.name", target = "type")
    @Mapping(source = "trainer.user", target = "trainerName", qualifiedByName = "fullName")
    TraineeTrainingDto toTraineeTrainingDto(Training training);


    List<TraineeTrainingDto> troTraineeTrainings(List<Training> trainings);

    @Named("fullName")
    default String fullName(User user) {
        return user.getFirstName() + " " + user.getLastName();
    }
}
