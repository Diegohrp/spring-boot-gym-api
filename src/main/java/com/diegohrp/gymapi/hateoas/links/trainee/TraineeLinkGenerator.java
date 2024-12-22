package com.diegohrp.gymapi.hateoas.links.trainee;

import com.diegohrp.gymapi.controller.TraineeController;
import com.diegohrp.gymapi.dto.trainee.CreateTraineeDto;
import com.diegohrp.gymapi.dto.trainee.TraineeProfileDto;
import com.diegohrp.gymapi.dto.trainings.TraineeTrainingDto;
import com.diegohrp.gymapi.dto.trainings.TraineeTrainingsParams;
import com.diegohrp.gymapi.dto.user.UserCreatedDto;
import com.diegohrp.gymapi.entity.user.Trainee;
import com.diegohrp.gymapi.entity.user.User;
import com.diegohrp.gymapi.hateoas.links.LinkGenerator;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TraineeLinkGenerator implements LinkGenerator {
    @Override
    public List<Link> generateLinks(String username, Object response, HttpMethod method) {

        Link self;
        Link getProfile = createLink(linkTo(methodOn(TraineeController.class).getTraineeProfile(username)).withRel("profile"), HttpMethod.GET.toString());
        Link updateProfile = createLink(linkTo(methodOn(TraineeController.class).updateTraineeProfile(username, null)).withRel("profile"), HttpMethod.PUT.toString());
        Link getTrainings = createLink(linkTo(methodOn(TraineeController.class).getTrainings(username, null, null, null, null)).withRel("trainings"), HttpMethod.GET.toString());
        Link deleteTrainee = createLink(linkTo(methodOn(TraineeController.class).deleteTrainee(username)).withRel("trainee"), HttpMethod.DELETE.toString());

        if (response instanceof UserCreatedDto) {
            self = linkTo(methodOn(TraineeController.class).create(null)).withSelfRel();
            return List.of(self, getProfile, updateProfile, getTrainings, deleteTrainee);
        }

        if (response instanceof TraineeProfileDto && method == HttpMethod.GET) {
            self = linkTo(methodOn(TraineeController.class).getTraineeProfile(username)).withSelfRel();
            return List.of(self, updateProfile, getTrainings, deleteTrainee);
        }

        if (response instanceof TraineeProfileDto && method == HttpMethod.PUT) {
            self = linkTo(methodOn(TraineeController.class).getTraineeProfile(username)).withSelfRel();
            return List.of(self, getProfile, getTrainings, deleteTrainee);
        }

        if (response instanceof TraineeTrainingsParams params) {
            self = linkTo(methodOn(TraineeController.class).getTrainings(username, params.periodFrom(), params.periodTo(), params.trainerName(), params.trainingType())).withSelfRel();
            return List.of(self, getProfile, updateProfile, deleteTrainee);
        }

        return List.of();
    }
}
