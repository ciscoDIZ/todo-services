package com.ciscoadiz.todo.projectsservice.resource;

import com.ciscoadiz.todo.projectsservice.api.UserService;
import com.ciscoadiz.todo.projectsservice.model.Project;
import com.ciscoadiz.todo.projectsservice.model.UserDTO;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.UUID;

@Path("/projects")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class ProjectResource {
    @Inject
    @RestClient
    UserService userService;

    @Channel("users")
    Uni<UserDTO> userDTOUni;

    @POST
    @Path("{ownerId}")
    public Uni<Response> create(@Context UriInfo uriInfo, @PathParam("ownerId") UUID ownerId, Project project) {
        return userService.getById(ownerId)
                .onItem().<Project>transformToUni(user -> {
                    project.owner = user.getId();
                    return project.persistAndFlush();
                }).onItem().transform(created -> {
                    UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(created.id.toString());
                    return Response.created(builder.build()).build();
                });
    }
}
