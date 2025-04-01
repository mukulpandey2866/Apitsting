package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.entity.Student;
import org.acme.repository.StudentRepository;

import java.util.List;

@Path("/")
public class StudentResource {

    @Inject
    StudentRepository studentRepository;

    @GET
    @Path("/getStudentList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentList () {
        List<Student> studentList = studentRepository.listAll();
        return Response.ok(studentList).build();
    }

}
