package com.diegohrp.gymapi.dto.trainings;

import java.util.Date;

public record TraineeTrainingsParams(Date periodFrom, Date periodTo, String trainerName, Long trainingType) {
}
