package uk.ac.leedsbeckett.Student_portal.services;

import uk.ac.leedsbeckett.Student_portal.model.Student;
import uk.ac.leedsbeckett.Student_portal.model.User;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    Student createStudent(Student student);
    Student getStudentById(long  studentId);
    Student getStudentByExternalStudentId(String externalStudentId);
    List<Student> getAllStudents();
    Optional<Student> getStudentByUser(User user);
    Student updateStudentByExternalStudentId(String externalStudentId, Student updatedStudent);
    void deleteStudentByExternalStudentId(String externalStudentId);
    void deleteAllStudents();
}


