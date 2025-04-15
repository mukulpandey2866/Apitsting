package org.acme.resource;

import static org.junit.jupiter.api.Assertions.*;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
@Tag("integreation")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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

  @Order(1)
  @Test
  void addStudent() {
    JsonObject jsonObject = Json.createObjectBuilder()
        .add("studentId",10L)
        .add("name","Elon Musk")
        .add("branch", "CS")
        .build();

    RestAssured.given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(jsonObject.toString())
        .when()
        .post("addStudent")
        .then()
        .statusCode(Response.Status.CREATED.getStatusCode());

  }

  @Order(2)
  @Test
  void getStudentById() {
    RestAssured.given()
               .when()
               .get("/student/10L")
               .then()
               .body("StudentId", equalTo(10))
               .body("name", equalTo("Elon Musk"))
               .body("branch", equalTo("CS"));
  }

  @Order(3)
  @Test
  void getAllStudentList() {
    RestAssured.given()
               .when()
               .get("getStudentList")
               .then()
               .body("size()", equalTo(6));
  }

} //add quarkus h2 depedency
// write test cases for PUT(check with updated id) and DELETE(size-1).