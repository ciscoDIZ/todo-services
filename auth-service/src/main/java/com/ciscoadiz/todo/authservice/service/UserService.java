package com.ciscoadiz.todo.authservice.service;


import com.ciscoadiz.todo.authservice.model.User;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserService {

    @Inject
    Logger logger;

    @ConfigProperty(name = "homework.security.iteration-count")
    Integer iterationCount;


    public Uni<User> create(User user) {
        return Panache.withTransaction(
                () -> Uni.createFrom().item(user).map(manufactured -> {
                                    manufactured.password = BcryptUtil
                                            .bcryptHash(manufactured.password, iterationCount);
                                    return manufactured;
                                }
                        )
                        .flatMap(created -> created.persist())
        );
    }

    public Uni<User> findById(UUID id) {
        return  User.findById(id);
    }

    public Uni<User> findByUsername(String username) {
        return User.find("username",username).firstResult();
    }

    public Uni<List<User>> findAll() {
        return User.findAll().list();
    }

    public Uni<User> updateById(UUID id, User user) {
        return Panache.withTransaction(() -> findById(id)
                .onItem().transformToUni(manufactured -> {
                    manufactured.name = user.name != null
                            ? user.name
                            : manufactured.name;
                    manufactured.password = user.password != null
                            ? BcryptUtil.bcryptHash(user.password)
                            : manufactured.password;
                    manufactured.username = user.username != null
                            ? user.username
                            : manufactured.username;
                    manufactured.roles = user.roles != null && user.roles.contains("admin")
                            ? "user,admin"
                            : manufactured.roles;
                    return manufactured.persist();
                }));
    }

    public Uni<Long> removeById(UUID id) {
        return User.delete("id", id);
    }
}
