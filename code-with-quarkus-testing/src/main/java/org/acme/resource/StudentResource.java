package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
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


    @GET
    @Path("/getCsStudentList")
    @Produces(MediaType.APPLICATION_JSON)
    List<Student> csStudentList = new ArrayList<>();
    public Response getCsStudentList () {
        List<Student> studentList = studentRepository.listAll();
        studentList.forEach(s -> {
            if (s.getBranch().equalsIgnoreCase("CS"))
                csStudentList.add(s);
        });
        return Response.ok(csStudentList).build();
    }

}

//http://127.0.0.1:9000/localproxy-5520869e.pac   proxy
//all done