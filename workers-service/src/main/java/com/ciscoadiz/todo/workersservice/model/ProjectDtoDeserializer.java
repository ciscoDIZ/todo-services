package com.ciscadiz.todo.workersservice.model;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class ProjectDtoDeserializer extends ObjectMapperDeserializer<ProjectDTO> {
    public ProjectDtoDeserializer() {
        super(ProjectDTO.class);
    }
}
