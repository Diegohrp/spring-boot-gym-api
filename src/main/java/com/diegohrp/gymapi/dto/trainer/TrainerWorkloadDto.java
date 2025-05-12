package com.diegohrp.gymapi.dto.trainer;

import com.diegohrp.gymapi.enums.ActionTypes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrainerWorkloadDto {
    private String username;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private LocalDate date;
    private Integer duration;
    private Integer currentWorkload;
    private ActionTypes actionType;
}
