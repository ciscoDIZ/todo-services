package com.ciscoadiz.todo.projectsservice.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "projects")
public class Project extends PanacheEntityBase {
    @Id
    @GeneratedValue
    public UUID id;
    public String name;
    public String description;
    public UUID owner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id)
                && Objects.equals(name, project.name)
                && Objects.equals(description, project.description)
                && Objects.equals(owner, project.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, owner);
    }
}
