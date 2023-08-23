package com.ciscoadiz.homework.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "todos")
@RegisterForReflection
public class Todo extends PanacheEntityBase {
    @Id
    @GeneratedValue
    public UUID id;
    public String title;
    public String description;

    @Enumerated(EnumType.STRING)
    public TodoStatus status;

    @CreationTimestamp
    @Column(name = "created_at")
    public Date createdAt;

    @UpdateTimestamp
    @Column(name = "last_update_at")
    public Date lastUpdateAt;
}
