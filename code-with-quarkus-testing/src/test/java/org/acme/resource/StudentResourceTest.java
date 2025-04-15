package org.acme.resource;

import static org.junit.jupiter.api.Assertions.*;

import com.google.inject.Inject;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import org.acme.entity.Student;
import org.acme.repository.StudentRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
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

  @Inject// the class to test
  StudentResource studentResource;

  @InjectMock // the class whose methods are to be avoided.
  StudentRepository studentRepository;

  Student student;
  @BeforeEach
  void setup() {
    student = new Student();
    student.setName("John");
    student.setBranch("EE");

  }






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







  @Test
  void createStudent() {
  }

  @Test
  void getAllStudent() {

    List<Student> studentList = new ArrayList<>();
    studentList.add(new Student(1L,"Shruti","CS"));
    studentList.add(new Student(2L,"Rahul","CS"));
    studentList.add(new Student(3L,"Akansha","CS"));

    Mockito.when(studentRepository.listAll()).thenReturn(studentList);

  }

  @Test
  void getCSStudentList() {
  }

  @Test
  void testGetStudentById() {
  }

  @Test
  void updateStudent() {
  }

  @Test
  void testUpdateStudent() {
  }

} //add quarkus h2 depedency
// write test cases for PUT(check with updated id) and DELETE(size-1).