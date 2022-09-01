package com.jersey.test.dao;

import com.jersey.test.base.GenericDaoImpl;
import com.jersey.test.data.Student;
import com.jersey.test.exception.ClientException;
import com.jersey.test.util.ValidUtil;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl extends GenericDaoImpl implements StudentDao {

//    public void saveStudent(Student student) {
//        Transaction transaction = null;
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            // start a transaction
//            transaction = session.beginTransaction();
//            // save the student object
//            session.save(student);
//            // commit transaction
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//        }
//    }


    @Override
    public List<Student> getStudents() throws Exception {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            List<Student> list= session.createQuery("from Student", Student.class).list();
//            return  list;
//        }
//        catch (Exception e){
//            throw new Exception(e);
//        }
        try {

            return getEntities("from Student", null);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }


    private Student StudentRequestToStudent(StudentRequest student) {
        return new Student(student.getFirstName(), student.getLastName(), student.getEmail());
    }

    @Override
    public Student addStudent(StudentRequest student) throws Exception {
        checkStudentInput(student.getEmail(), student.getLastName());
        return addEntity(StudentRequestToStudent(student));
    }

    @Override
    public Student updateStudent(Student student, String id) throws Exception {
        if (!ValidUtil.isValidNumber(id)) {
            throw new ClientException("id格式錯誤");
        }
        checkStudentInput(student.getEmail(), student.getLastName());
        Student newStudent = getStudentById(id);
        if (newStudent == null) {
            throw new ClientException("找不到此學生");
        }

        newStudent.setEmail(student.getEmail());
        newStudent.setFirstName(student.getFirstName());
        if (student.getFirstName() != null && !student.getFirstName().isEmpty())
            newStudent.setFirstName(student.getFirstName());
        return updateEntity(newStudent);

    }

    private void checkStudentInput(String email, String lastName) throws ClientException {
        if (email == null || email.isEmpty()) {
            throw new ClientException("Email為必填");
        } else if (!ValidUtil.isValidEmail(email)) {
            throw new ClientException("Email格式錯誤");
        }
        if (lastName == null || lastName.isEmpty()) {
            throw new ClientException("LastName為必填");
        }
    }

    @Override
    public Student getStudentById(String id) throws Exception {
        if (!ValidUtil.isValidNumber(id)) {
            throw new ClientException("id格式錯誤");
        }
        String query = "from Student where id = ?0";
        List<Object> list = new ArrayList<Object>();
        list.add(Integer.parseInt(id));
        Student product = getEntity(query, list);
        if (product == null)
            throw new ClientException(Response.Status.NOT_FOUND, null, "找不到此student");
        return product;
    }


    @Override
    public boolean deleteStudent(String id) throws Exception {
        if (!ValidUtil.isValidNumber(id)) {
            throw new ClientException("id格式錯誤");
        }
        Student delStudent = getStudentById(id);
        if (delStudent == null) {
            throw new ClientException(Response.Status.NOT_FOUND, null, "找不到此學生");
        }
        return deleteEntity(delStudent);
    }
}
