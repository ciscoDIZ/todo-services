package com.ciscoadiz.todo.authservice.resource;


import com.ciscoadiz.todo.authservice.model.User;
import com.ciscoadiz.todo.authservice.service.UserService;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;


@Path("/auth")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    UserService service;

    @Inject
    Logger logger;


    @POST
    public Uni<Response> singUp(@Valid User user) {
        return Panache.withSession(() -> service.create(user))
                .onItem().transform(created -> {
                    UriBuilder builder = UriBuilder.fromResource(UserResource.class).path(created.id.toString());
                    return Response.created(builder.build()).build();
                })
                .onFailure(ConstraintViolationException.class).transform(throwable -> {
                    JsonObjectBuilder builder = Json.createObjectBuilder();
                    builder.add("trace", throwable.getLocalizedMessage());
                    return new BadRequestException(Response.status(400).entity(builder.build()).build());
                });
    }
}