package com.ciscoadiz.todo.authservice.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.runtime.annotations.RegisterForReflection;
import io.smallrye.mutiny.Uni;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@RegisterForReflection
@Table(name = "users")
public class User extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public UUID id;

    public String name;


    @Column(unique = true)
    public String username;

    public String password;

    @ColumnDefault("'user'")
    public String roles;

    @Column(name = "created_at")
    @CreationTimestamp
    public Date createdAt;

    @UpdateTimestamp
    @Column(name = "last_update_at")
    public Date lastUpdatedAt;

    public static Uni<User> findByUsername(String username) {
        return find("username", username).firstResult();
    }
}
