package com.diegohrp.gymapi.mapper.annotation;


import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapping(source = "entity.user.id", target = "id")
@MapUserWithoutId
public @interface MapUserAtributes {
}
