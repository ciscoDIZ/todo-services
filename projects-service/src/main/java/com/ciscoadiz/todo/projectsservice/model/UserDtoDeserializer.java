package com.ciscoadiz.todo.projectsservice.model;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class UserDtoDeserializer extends ObjectMapperDeserializer<UserDTO> {
    public UserDtoDeserializer() {
        super(UserDTO.class);
    }
}
