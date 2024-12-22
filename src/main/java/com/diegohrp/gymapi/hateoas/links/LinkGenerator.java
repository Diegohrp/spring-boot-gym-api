package com.diegohrp.gymapi.hateoas.links;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;

import java.util.List;

public interface LinkGenerator {
    List<Link> generateLinks(String username, Object response, HttpMethod method);

    default Link createLink(Link link, String httpMethod) {
        return Link.of(link.getHref(), httpMethod + "_" + link.getRel().value());
    }
}

