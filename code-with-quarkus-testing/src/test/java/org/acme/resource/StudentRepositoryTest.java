package org.acme.resource;

import static io.smallrye.common.constraint.Assert.assertFalse;
import static io.smallrye.common.constraint.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import java.util.List;
import org.acme.entity.Student;
import org.acme.repository.StudentRepository;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class StudentRepositoryTest {
  @InjectMock
  StudentRepository studentRepository;

  @Test
  void listAll(){
    List<Student> studentList = studentRepository.listAll();

    assertFalse(studentList.isEmpty());
    assertEquals(studentList.size(),6);
    assertEquals(studentList.get(0).getName(),"John");

  }

  @Test
  void findById(){
    Student student = studentRepository.findById(6L);

    assertNotNull(student);
    assertEquals(student.getStudentId(),6L);
    assertEquals(student.getName(),"John");
    assertEquals(student.getBranch(),"ME");

  }
  @Test
  void getStudentListByBranch(){
    List<Student> studentList = studentRepository.getStudentListByBranch("EE");

    assertFalse(studentList.isEmpty());
    assertEquals(studentList.size(),2);
  }

}
