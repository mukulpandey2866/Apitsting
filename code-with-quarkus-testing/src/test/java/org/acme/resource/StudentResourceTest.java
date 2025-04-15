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
import jakarta.ws.rs.core.Response.Status;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
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
    Student s = new Student(1L,"rahul","CS");

    Mockito.doNothing().when(studentRepository).persist(s);
    Mockito.when(studentRepository.isPersistent(s)).thenReturn(true);

    Response response = studentResource.createStudent(s);

    assertNotNull(response);
    assertNotNull(response.getEntity());
    assertEquals(response.getStatus(), Status.CREATED.getStatusCode());
    assertNotNull(response.getLocation());

  }

  @Test
  void getAllStudent() {

    List<Student> studentList = new ArrayList<>();
    studentList.add(new Student(1L, "Shruti", "CS"));
    studentList.add(new Student(2L, "Rahul", "CS"));
    studentList.add(new Student(3L, "Akansha", "CS"));

    Mockito.when(studentRepository.listAll()).thenReturn(studentList);

    Response response = studentResource.getAllStudent();

    assertNotNull(response);
    assertNotNull(response.getEntity());
    assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());

    List<Student> studentList1 = (List<Student>) response.getEntity();
    assertEquals(studentList.size(), 3);

  }



    @Test
    void testGetStudentById () {

      Student student1 = new Student(4L, "Rajesh", "ME");
      Mockito.when(studentRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(student1);

      Response response = studentResource.getStudentById(4L);
      assertNotNull(response);
      assertNotNull(response.getEntity());
      assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());

      Student student2 = (Student) response.getEntity();

      assertEquals(student2.getName(), "Rajesh");
      assertEquals(student2.getBranch(), "ME");


    }

  @Test
  void testGetStudentByIdKO () {

    Student student1 = new Student(4L, "Rajesh", "ME");
    Mockito.when(studentRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(null);

    Response response = studentResource.getStudentById(4L);
    assertNotNull(response);
    assertEquals(response.getStatus(), Status.NO_CONTENT.getStatusCode());

  }

    @Test
    void updateStudent () {

      Student student1 = new Student(4L, "Rajesh", "ME");
      Mockito.when(studentRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(student1);

      Student newUpdateStudent = new Student();
      newUpdateStudent.setName("Mahesh");
      Response response=studentResource.updateStudent(newUpdateStudent,4L);

      assertNotNull(response);
      assertNotNull(response.getEntity());
      assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());

      Student sr=(Student) response.getEntity();

      assertEquals(sr.getName(), "Mahesh");
      assertEquals(sr.getBranch(), "ME");

    }


  }

//}
//add quarkus h2 depedency
// write test cases for PUT(check with updated id) and DELETE(size-1).