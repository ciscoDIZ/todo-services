package com.ciscadiz.todo.workersservice.model;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class UserDtoDeserializer extends ObjectMapperDeserializer<UserDTO> {
    public UserDtoDeserializer() {
        super(UserDTO.class);
    }
}
