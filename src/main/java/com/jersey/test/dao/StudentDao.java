package com.jersey.test.dao;

import com.jersey.test.data.Student;

import java.util.List;

public interface StudentDao {
    // category related stubs
    public Student addStudent(StudentRequest Product) throws Exception;
    public Student updateStudent(Student Product, String id) throws Exception;
    public Student getStudentById(String id) throws Exception;
    public List<Student> getStudents() throws Exception;
    public boolean deleteStudent(String id) throws Exception;
}
