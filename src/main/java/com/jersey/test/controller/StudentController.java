package com.jersey.test.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jersey.test.base.BaseException;
import com.jersey.test.base.BaseResponse;
import com.jersey.test.dao.StudentDAOImpl;
import com.jersey.test.dao.StudentRequest;
import com.jersey.test.dao.UpdateStudentRes;
import com.jersey.test.data.Student;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/api/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentController {

    private StudentDAOImpl stDAOImpl = new StudentDAOImpl();
    private Gson gson = new GsonBuilder()
            .setPrettyPrinting() // build json formatted (optional)
            .serializeNulls() // include null fields
            .create();


    @GET
    @Path("/")
    public Response findStudent() {

        try {
            List<Student> list = stDAOImpl.getStudents();
            return Response.status(Response.Status.OK).entity(gson.toJson(list)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return handleInteralServerError(e.getMessage());
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateStudent(Student student,
                                  @PathParam("id") String itemId) {
        Response response = null;
        try {
            response = Response.status(Response.Status.OK)
                    .entity(gson.toJson(new UpdateStudentRes(stDAOImpl.updateStudent(
                            student, itemId).getId()))).build();
        } catch (BaseException e) {
            return e.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return handleInteralServerError(e.getMessage());
        }
        return response;
    }


    @GET
    @Path("/{id}")
    public Response getStudent(@PathParam("id") String id) throws Exception {

        try {
            return Response.status(Response.Status.OK)
                    .entity(gson.toJson(stDAOImpl.getStudentById(id))).build();
        } catch (BaseException e) {
            return e.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return handleInteralServerError(e.getMessage());
        }

    }

    @DELETE
    @Path("/{id}")
    public Response deleteStudentById(@PathParam("id") String id) {
        try {
            if (stDAOImpl.deleteStudent(id)) {
                return Response.noContent().entity(gson.toJson(new BaseResponse("刪除學生成功"))).build();
            } else {
                return Response.noContent().entity(gson.toJson(new BaseResponse("刪除學生失敗"))).build();
            }
        } catch (BaseException e) {
            return e.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return handleInteralServerError(e.getMessage());
        }

    }

    @POST
    @Path("/")
    public Response addStudent(StudentRequest data) {
        try {
            stDAOImpl.addStudent(data);
            return Response.status(Response.Status.CREATED).entity(gson.toJson(new BaseResponse("新增學生成功"))).build();
        } catch (BaseException e) {
            return e.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return handleInteralServerError(e.getMessage());

        }
    }

    private Response handleInteralServerError(String message) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new BaseResponse(message)).build();
    }
}
