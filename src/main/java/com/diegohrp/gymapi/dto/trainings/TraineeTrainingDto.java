package com.diegohrp.gymapi.dto.trainings;

import java.util.Date;

public record TraineeTrainingDto(Long id, String name, Date date, String type, Integer duration, String trainerName) {
}
