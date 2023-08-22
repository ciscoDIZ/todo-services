package com.ciscoadiz.homework;

import com.ciscoadiz.homework.model.Todo;
import com.ciscoadiz.homework.model.TodoStatus;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@QuarkusTest
public class TodoResourceTest {

    @Inject
    Logger logger;

    @Test
    public void create_should_be_created_and_have_valid_location_header() {
        String location = given()
                .body("{\"title\": \"irrelevant\", \"description\": \"irrelevant\", \"status\": \"STOPPED\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .when().post("/api/todos")
                .then().statusCode(Response.Status.CREATED.getStatusCode()).extract().header("Location");
        String[] locationAsArray = location.split("/");
        int expectedLength = 6;
        String expectedEndPoint = "todos";
        assertEquals(expectedLength, locationAsArray.length);
        assertEquals(expectedEndPoint, locationAsArray[locationAsArray.length - 2]);
    }

    @Test
    public void get_by_id_should_be_ok() {
        given()
                .get("/api/todos/{id}", "a7353852-5a1e-4e01-8acc-6f268235ff1f")
                .then()
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    public void update_by_id_should_be_accepted_and_have_valid_body() {
        Todo todo = new Todo();
        String id = "a7353852-5a1e-4e01-8acc-6f268235ff1f";
        todo.description = "esta descripción mola más";
        todo.status = TodoStatus.STARTED;
        Todo updated = given()
                .body(todo).contentType(MediaType.APPLICATION_JSON)
                .when().patch("/api/todos/{id}", id)
                .then().statusCode(Response.Status.ACCEPTED.getStatusCode()).extract()
                .body().as(Todo.class);
        logger.debugf("executed update_by_id_should_be_created_and_have_valid_body() under todo with id %s", id);
        assertEquals(todo.description, updated.description);
        assertEquals(todo.status, updated.status);
    }

    @Test
    public void get_all_should_be_ok_and_have_valid_body() {
        List todos = given().get("/api/todos").then().contentType(MediaType.APPLICATION_JSON).statusCode(200).extract().body().as(ArrayList.class);

        assertFalse(todos.isEmpty());
    }
    @Test
    public void delete_should_be_no_content() {
        String id = "701cf4c5-676b-4811-9791-961476c3440d";
       given().delete("/api/todos/{id}", id).then().statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }
}
