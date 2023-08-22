package com.ciscoadiz.homework.validation;


import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage(e)).build();
    }

    private JsonObject errorMessage(ConstraintViolationException exception) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        for (ConstraintViolation constraintViolation : exception.getConstraintViolations()) {
            builder.add(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
        }
        return builder.build();
    }
}
