package com.ciscoadiz.homework.resource;

import com.ciscoadiz.homework.model.User;
import com.ciscoadiz.homework.service.UserService;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;


@Path("users")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    @Inject
    UserService service;

    @GET
    public Uni<List<User>> getAll() {
        return Panache.withSession(() -> service.findAll());
    }

    @GET
    @Path("{id}")
    public Uni<Response> getById(@PathParam("id") UUID id) {
        return  Panache.withSession(() -> service.findById(id))
                .map(user -> Response.ok(user).build())
                .onItem().ifNull()
                .failWith(NotFoundException::new);
    }

    @GET
    @Path("/username/{username}")
    public Uni<Response> getByUsername(@PathParam("username") String username) {
        return Panache.withSession(() -> service.findByUsername(username))
                .map(user -> Response.ok(user).build())
                .onItem().ifNull()
                .failWith(NotFoundException::new);
    }

    @PATCH
    @Path("{id}")
    public Uni<Response> updateById(@PathParam("id") UUID id, User user) {
        return Panache.withSession(() -> service.updateById(id, user)).map(updated -> Response.accepted(updated).build());
    }

    @DELETE
    @Path("{id}")
    public Uni<Response> deleteById(@PathParam("id") UUID id) {
        return Panache.withSession(() -> service.removeById(id)).map(
                result -> result > 0 ?
                        Response.noContent().build() :
                        Response.status(404).build()
        );
    }
}
