package com.ciscoadiz.todo.authservice.messaging;

import com.ciscoadiz.todo.authservice.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

@ApplicationScoped
public class UserEmitter {
    private final static Logger LOGGER = Logger.getLogger(UserEmitter.class);

    @Channel("users")
    Emitter<User> emitter;

    public void sendUser(User user) {
        emitter.send(user);
    }
}
