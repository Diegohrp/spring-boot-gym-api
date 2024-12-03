package com.diegohrp.gymapi.mapper;

import com.diegohrp.gymapi.dto.trainings.TraineeTrainingDto;
import com.diegohrp.gymapi.dto.trainings.TrainerTrainingDto;
import com.diegohrp.gymapi.dto.trainings.TrainingTypeDto;
import com.diegohrp.gymapi.entity.training.Training;
import com.diegohrp.gymapi.entity.training.TrainingType;
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

    @Mapping(source = "type.name", target = "type")
    @Mapping(source = "trainee.user", target = "traineeName", qualifiedByName = "fullName")
    TrainerTrainingDto toTrainerTrainingDto(Training training);

    List<TrainerTrainingDto> toTrainerTrainings(List<Training> trainings);


    TrainingTypeDto toTrainingType(TrainingType trainingType);

    List<TrainingTypeDto> toTrainingTypesList(List<TrainingType> typesList);

    @Named("fullName")
    default String fullName(User user) {
        return user.getFirstName() + " " + user.getLastName();
    }
}
