package com.diegohrp.gymapi.hateoas.links;

import com.diegohrp.gymapi.hateoas.links.trainee.TraineeLinkGenerator;
import com.diegohrp.gymapi.hateoas.links.trainer.TrainerLinkGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LinkGeneratorFacotry {
    private final TraineeLinkGenerator traineeLinkGenerator;
    private final TrainerLinkGenerator trainerLinkGenerator;

    public LinkGenerator getLinkGenerator(String type) {
        switch (type) {
            case "trainee":
                return traineeLinkGenerator;
            case "trainer":
                return trainerLinkGenerator;
            default:
                throw new IllegalArgumentException("Tipo de LinkGenerator no reconocido: " + type);
        }
    }
}
