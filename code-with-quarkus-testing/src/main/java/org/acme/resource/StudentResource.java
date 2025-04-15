package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.net.URI;
import java.util.ArrayList;
import org.acme.entity.Student;
import org.acme.repository.StudentRepository;

import java.util.List;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)  // now it applies to the entire class
public class StudentResource {

    @Inject
    StudentRepository studentRepository;

//    @GET
//    @Path("/getStudentList")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getStudentList () {
//        List<Student> studentList = studentRepository.listAll();
//        return Response.ok(studentList).build();
//    }
//
//
//    @GET
//    @Path("/getCsStudentList")
//    @Produces(MediaType.APPLICATION_JSON)
//    List<Student> csStudentList = new ArrayList<>();
//    public Response getCsStudentList () {
//        List<Student> studentList = studentRepository.listAll();
//        studentList.forEach(s -> {
//            if (s.getBranch().equalsIgnoreCase("CS"))
//                csStudentList.add(s);
//        });
//        return Response.ok(csStudentList).build();
//    }
//
//    @POST
//    @Path("/addStudent")
//    @Transactional // to save and all
////    @Consumes(MediaType.APPLICATION_JSON)
////    @Produces(MediaType.APPLICATION_JSON)   // applies to the entire class
//    public Response addStudent(Student student) {
//        studentRepository.isPersistent(student);
//        if(studentRepository.isPersistent(student)){
//            //localhost://8080/student/id
//            return Response.created(URI.create("/student/" + student.getStudentId())).build();
//        }
//        return Response.ok(Response.status(Response.Status.BAD_REQUEST)).build();
//
//
//    }
//
//
//    @GET
//    @Path("/student/{id}")
//    @Transactional
////    @Consumes(MediaType.APPLICATION_JSON)
////    @Produces(MediaType.APPLICATION_JSON)   // applies to the entire class
//
//    public Response getStudentById(@PathParam("id") Long id) {
//        Student student = studentRepository.findById(id);
//        if (student == null)
//            return Response.ok(Response.status(Response.Status.NOT_FOUND)).build();
//        else
//            return Response.ok(student).build();
//    }
//
//
//    @GET
//    @Path("/student/{id}")
//    @Transactional
//    public Response getStudentById(@PathParam("id") Long id) {
//        Student student = studentRepository.findById(id);
//        if (student == null)
//            return Response.ok(Response.status(Response.Status.NOT_FOUND)).build();
//        else
//            return Response.ok(student).build();
//    }
//
//    @GET
//    @Path("getAllStudent")
//    @Transactional
//    public Response getAllStudentList() {
//        return Response.ok(studentRepository.listAll()).build();
//    }
//


    @POST
    public Response createStudent(@RequestBody Student student) {
        studentRepository.persist(student);
        if(studentRepository.isPersistent(student))
            return Response.created(URI.create("/student/" + student.getStudentId())).build();
        else return Response.ok(Response.status(Response.Status.BAD_REQUEST)).build();

    }

    @GET
    public Response getAllStudent(){
        List<Student> studentList= studentRepository.listAll();
        return Response.ok(studentList).build();
    }

    @GET
    @Path("branch/{branch}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCSStudentList(@PathParam("branch") String branch) {
        List<Student> studentList = studentRepository.list("branch", branch);
        return Response.ok(studentList).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentById(@PathParam("id") Long id){
        Student student = studentRepository.findById(id);
        if(student != null)
            return Response.ok(student).build();
        else return Response.status(Response.Status.NO_CONTENT).build();
    }

    @POST
    @Path("update/{id}")
    public Response updateStudent(@RequestBody Student studentUpdate, @PathParam("id") Long id){
        Student student = studentRepository.findById(id);
        if(student != null)
        {
            student.setName(studentUpdate.getName());
            return Response.ok(student).build();
        }else return Response.status(Response.Status.BAD_REQUEST).build();

    }

    @POST
    @Path("delete/{id}")
    public Response updateStudent(@PathParam("id") Long id){
        boolean isDeleted = studentRepository.deleteById(id);
        if(isDeleted)
            return Response.status(Response.Status.NO_CONTENT).build();
        else return Response.status(Response.Status.NOT_FOUND).build();
    }


}

//http://127.0.0.1:9000/localproxy-5520869e.pac   proxy
//all done