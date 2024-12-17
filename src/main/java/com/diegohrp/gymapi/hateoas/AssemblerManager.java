package com.diegohrp.gymapi.hateoas;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AssemblerManager {
    private final ApplicationContext context;
    private final Map<Class<?>, GenericAssembler<?>> assemblers = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> GenericAssembler<T> getAssembler(Class<T> dtoClass) {
        return (GenericAssembler<T>) assemblers.computeIfAbsent(dtoClass, key -> context.getBean(GenericAssembler.class));
    }
}
