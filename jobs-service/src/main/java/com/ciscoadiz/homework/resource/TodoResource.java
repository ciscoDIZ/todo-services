package com.ciscoadiz.homework.resource;

import com.ciscoadiz.homework.model.Todo;
import com.ciscoadiz.homework.service.TodoService;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import java.util.ArrayList;
import java.util.UUID;

@Path("/todos")
public class TodoResource {

    @Inject
    TodoService service;

    @POST
    public Uni<Response> create(@Context UriInfo uriInfo, Todo todo) {
        return Panache.withSession(() -> service.create(todo))
                .onItem().transform(created -> {
                    UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(created.id.toString());
                    return Response.created(builder.build()).build();
                });
    }

    @GET
    @Path("{id}")
    public Uni<Response> getById(@PathParam("id") UUID id) {
        return Panache.withSession(() -> service.findById(id))
                .onItem().transform(todo -> Response.ok(todo).build());
    }

    @PATCH
    @Path("{id}")
    public Uni<Response> updateById(@PathParam("id") UUID id, Todo todo) {
        return Panache.withSession(() -> service.updateById(id, todo))
                .onItem().transform((updated) -> Response.accepted(updated).build());
    }

    @GET
    public Uni<Response> getAll() {
        return Panache.withSession(() -> service.findAll())
                .onItem().transform(todos -> Response.ok(todos).build());
    }

    @DELETE
    @Path("{id}")
    public Uni<Response> deleteById(@PathParam("id") UUID id) {
        return Panache.withSession(() -> service.deleteById(id)).onItem().transform(result -> result > 0 ? Response.noContent().build(): Response.status(404).build());
    }
}
