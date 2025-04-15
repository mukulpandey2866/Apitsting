package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import org.acme.entity.Student;

@ApplicationScoped
public class StudentRepository implements PanacheRepository<Student> {

  public List<Student> getStudentListByBranch(String branch) {
    return list("select s from student s where s.branch =?1", branch);
  }
}
