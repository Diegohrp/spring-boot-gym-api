package com.diegohrp.gymapi.dto.trainings;

import java.util.Date;

public record TrainerTrainingsParams(Date periodFrom, Date periodTo, String traineeName) {
}
