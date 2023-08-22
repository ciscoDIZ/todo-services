package com.ciscoadiz.homework.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
import java.util.Optional;
import java.util.UUID;

@Entity
@RegisterForReflection
@Table(name = "users")
@NamedQueries({
        @NamedQuery(
                name = "User.findAllWithTodos",
                query = "select u from User u left join fetch u.todos"
        ),
        @NamedQuery(
                name = "User.findByIdWithTodos",
                query = "select u from User u left join fetch u.todos where u.id = :id"
        ),
        @NamedQuery(
                name = "User.findByUsernameWithTodos",
                query = "select u from User u left join fetch u.todos where u.username = :username"
        ),
        @NamedQuery(
                name = "User.findAll",
                query = "select u from User u"
        )
    }
)
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
    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true
    )
    @JsonManagedReference
    public List<Todo> todos;

    @Column(name = "created_at")
    @CreationTimestamp
    public Date createdAt;

    @UpdateTimestamp
    @Column(name = "last_update_at")
    public Date lastUpdatedAt;

    public static Uni<User> findByUsername(String username) {
        return find("username", username).firstResult();
    }

    public static Uni<User> findByIdWithTodos(UUID id) {
        return getSession().flatMap(session -> session.createNamedQuery("User.findByIdWithTodos", User.class).setParameter("id", id).getSingleResult());
    }
    public static Uni<List<User>> findAllWithTodos() {
        return getSession().flatMap(session -> session.createNamedQuery("User.findAllWithTodos", User.class).getResultList());
    }
    public static Uni<User> findByUsernameWithTodos(String username) {
        return getSession()
                .flatMap(session -> session.createNamedQuery("User.findByUsernameWithTodos", User.class)
                        .setParameter("username", username)
                        .getSingleResult()
                );
    }
}
