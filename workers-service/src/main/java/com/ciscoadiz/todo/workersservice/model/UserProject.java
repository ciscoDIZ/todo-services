package com.ciscadiz.todo.workersservice.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "users_projects")
public class UserProject extends PanacheEntityBase {

    @EmbeddedId
    public UserProjectId userProjectId;

    @Enumerated(EnumType.STRING)
    public ProjectPermissions permissions;
}
