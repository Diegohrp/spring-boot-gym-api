package com.diegohrp.gymapi.mapper.annotation;

import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mappings({
        @Mapping(source = "entity.user.username", target = "username"),
        @Mapping(source = "entity.user.firstName", target = "firstName"),
        @Mapping(source = "entity.user.lastName", target = "lastName")})
public @interface MapUserWithoutId {
}
