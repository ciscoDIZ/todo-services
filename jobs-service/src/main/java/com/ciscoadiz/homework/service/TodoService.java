package com.ciscoadiz.homework.service;

import com.ciscoadiz.homework.model.Todo;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TodoService {
    public Uni<Todo> create(Todo todo) {
        return Panache.withTransaction(todo::persistAndFlush);
    }

    public Uni<Todo> findById(UUID id) {
        return Panache.withSession(() -> Todo.find("id", id).firstResult());
    }

    public Uni<Todo> updateById(UUID id, Todo todo) {
        return Panache.withTransaction(() -> findById(id)
                .onItem().transformToUni(updated -> {
                    updated.title = todo.title != null
                            ? todo.title
                            : updated.title;
                    updated.description = todo.description != null
                            ? todo.description
                            : updated.description;
                    updated.status = todo.status != null
                            ? todo.status
                            : updated.status;
                    return updated.persistAndFlush();
                }));
    }
    public Uni<List<Todo>> findAll() {
        return Panache.withSession(Todo.findAll()::list);
    }
    public Uni<Long> deleteById(UUID id) {
        return Panache.withTransaction(() -> Todo.delete("id", id));
    }
}
