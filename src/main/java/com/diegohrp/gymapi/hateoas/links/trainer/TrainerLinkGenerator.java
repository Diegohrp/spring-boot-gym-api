package com.diegohrp.gymapi.hateoas.links.trainer;

import com.diegohrp.gymapi.controller.TraineeController;
import com.diegohrp.gymapi.controller.TrainerController;
import com.diegohrp.gymapi.dto.trainee.TraineeProfileDto;
import com.diegohrp.gymapi.dto.trainer.TrainerProfileDto;
import com.diegohrp.gymapi.dto.trainer.TrainerSummaryDto;
import com.diegohrp.gymapi.dto.trainings.TraineeTrainingsParams;
import com.diegohrp.gymapi.dto.trainings.TrainerTrainingsParams;
import com.diegohrp.gymapi.dto.user.UserCreatedDto;
import com.diegohrp.gymapi.hateoas.links.LinkGenerator;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TrainerLinkGenerator implements LinkGenerator {
    @Override
    public List<Link> generateLinks(String username, Object response, HttpMethod method) {
        Link self;
        Link getProfile = createLink(linkTo(methodOn(TrainerController.class).getTrainerProfile(username)).withRel("profile"), HttpMethod.GET.toString());
        Link updateProfile = createLink(linkTo(methodOn(TrainerController.class).updateTrainerProfile(username, null)).withRel("profile"), HttpMethod.PUT.toString());
        Link getTrainings = createLink(linkTo(methodOn(TrainerController.class).getTrainings(username, null, null, username)).withRel("trainings"), HttpMethod.GET.toString());
        Link getUnassigned = createLink(linkTo(methodOn(TrainerController.class).getUnassignedTrainers(null)).withRel("unassigned_trainers"), HttpMethod.GET.toString());


        if (response instanceof UserCreatedDto) {
            self = linkTo(methodOn(TrainerController.class).create(null)).withSelfRel();
            return List.of(self, getProfile, updateProfile, getTrainings, getUnassigned);
        }

        if (response instanceof TrainerProfileDto && method == HttpMethod.GET) {
            self = linkTo(methodOn(TrainerController.class).getTrainerProfile(username)).withSelfRel();
            return List.of(self, updateProfile, getTrainings, getUnassigned);
        }

        if (response instanceof TrainerProfileDto && method == HttpMethod.PUT) {
            self = linkTo(methodOn(TrainerController.class).getTrainerProfile(username)).withSelfRel();
            return List.of(self, getProfile, getTrainings, getUnassigned);
        }

        if (response instanceof TrainerTrainingsParams params) {
            self = linkTo(methodOn(TrainerController.class).getTrainings(username, params.periodFrom(), params.periodTo(), params.traineeName())).withSelfRel();
            return List.of(self, getProfile, updateProfile, getUnassigned);
        }

        if (response instanceof String) {
            self = linkTo(methodOn(TrainerController.class).getUnassignedTrainers(username)).withSelfRel();
            return List.of(self, getProfile, updateProfile, getTrainings);
        }

        return List.of();
    }
}
