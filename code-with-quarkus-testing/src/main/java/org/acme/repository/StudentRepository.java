package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Student;

@ApplicationScoped
public class StudentRepository implements PanacheRepository<Student> {

}
