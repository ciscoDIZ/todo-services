package com.ciscoadiz.homework;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import com.ciscoadiz.homework.model.User;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;



@QuarkusTest
public class UserResourceTest {
    @Inject
    Logger logger;

    @Test
    public void get_by_id_should_be_ok_and_have_a_valid_body() {
        User user = given()
                .get("/api/users/{id}", "026ec571-f271-4ab4-95e5-00b49c260795")
                .then().statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .extract()
                .body()
                .as(User.class);
        assertEquals("026ec571-f271-4ab4-95e5-00b49c260795", user.id.toString());
    }

    @Test
    public void get_by_username_should_be_ok_and_have_a_valid_body() {
        User user = given()
                .get("/api/users/username/{username}", "Pepito")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .extract()
                .body().as(User.class);
        assertEquals("Pepito", user.username);
    }

    @Test
    public void update_by_id_should_be_accepted_and_have_a_valid_body() {
        User updated = given()
                .when()
                .body("{\"username\": \"Pepito\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .patch("/api/users/{id}", "026ec571-f271-4ab4-95e5-00b49c260795")
                .then()
                .statusCode(Response.Status.ACCEPTED.getStatusCode())
                .extract()
                .body().as(User.class);
        assertEquals("Pepito", updated.username);
    }

    @Test
    public void get_all_should_be_ok_and_have_a_valid_body() {
        List users = given()
                .when()
                .get("/api/users")
                .then().statusCode(200)
                .extract()
                .body().as(ArrayList.class);
        Assertions.assertFalse(users.isEmpty());
    }

    @Test
    public void delete_by_id_should_be_no_content() {
        given().when()
                .contentType(MediaType.APPLICATION_JSON)
                .delete("/api/users/{id}", "34c5a104-18ca-4f6e-b219-851d6296a7a5")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }
}
