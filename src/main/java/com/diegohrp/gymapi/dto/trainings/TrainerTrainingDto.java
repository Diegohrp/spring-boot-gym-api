package com.diegohrp.gymapi.dto.trainings;

import java.util.Date;

public record TrainerTrainingDto(Long id, String name, Date date, String type, Integer duration, String traineeName) {
}
