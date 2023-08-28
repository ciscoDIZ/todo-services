package com.ciscoadiz.todo.authservice;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class AuthResourceTest {
    @Inject
    Logger logger;
    @Test
    public void create_should_be_accepted_and_have_a_valid_location_header() {
       String location = given()
               .body("{\"name\": \"irrelevant\", \"username\": \"irrelevant\", \"password\": \"irrelevant\"}")
               .contentType(MediaType.APPLICATION_JSON)
               .when()
               .post("/auth").then()
               .statusCode(Response.Status.CREATED.getStatusCode())
               .extract().headers().get("Location").getValue();
        String[] locationAsArray = location.split("/");
        String expectedEndpoint = locationAsArray[locationAsArray.length - 2];
        int expectedLength = 6;
        logger.debugf("executed create with location headers: %s", location);
        assertEquals(expectedLength, locationAsArray.length);
        assertEquals(expectedEndpoint, "users");
   }
}