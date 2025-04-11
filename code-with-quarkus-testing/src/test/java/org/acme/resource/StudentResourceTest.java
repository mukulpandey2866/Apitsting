package org.acme.resource;

import static org.junit.jupiter.api.Assertions.*;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.ws.rs.Path;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
@Tag("integreation")
@Path("/")
class StudentResourceTest {

  @Test
  void getStudentList() {


    //change the database to "none" from "drop and create".
    RestAssured.
        given()
        .when()
        .get("/getStudentList")
        .then()
        .body("size()", equalTo(4))
        .body("name", hasItems("John Doe", "Jane Doe"))
        .body("branch", hasItems("EE", "CS"));
  }

  @Test
  void getCsStudentList() {

    RestAssured.
        given()
        .when()
        .get("/getCsStudentList")
        .then()
        .body("size()", equalTo(2))
        .body("name", hasItems("John Doe", "Jane Doe"));
  }

}