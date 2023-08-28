package com.ciscoadiz.todo.projectsservice.api;

import com.ciscoadiz.todo.projectsservice.model.UserDTO;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.UUID;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "ciscoadiz-user-service")
public interface UserService {
    @GET
    @Path("{id}")
    Uni<UserDTO> getById(UUID id);
}
