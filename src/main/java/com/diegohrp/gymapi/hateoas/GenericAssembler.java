package com.diegohrp.gymapi.hateoas;

import com.diegohrp.gymapi.hateoas.links.LinkGenerator;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class GenericAssembler<T> implements RepresentationModelAssembler<T, EntityModel<T>> {
    private final LinkGenerator linkGenerator;

    @Override
    public EntityModel<T> toModel(T entity) {
        return EntityModel.of(entity);
    }

    public EntityModel<T> toModel(T entity, String username, HttpMethod method) {
        EntityModel<T> entityModel = this.toModel(entity);
        entityModel.add(linkGenerator.generateLinks(username, entity, method));
        return entityModel;
    }

    public CollectionModel<EntityModel<T>> toCollectionModel(List<T> entities, String username, Object params, HttpMethod method) {
        List<EntityModel<T>> entityModels = entities.stream()
                .map(this::toModel)
                .toList();
        CollectionModel<EntityModel<T>> collectionModel = CollectionModel.of(entityModels);
        collectionModel.add(linkGenerator.generateLinks(username, params, method));
        return collectionModel;
    }
}