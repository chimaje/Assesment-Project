package uk.ac.leedsbeckett.Student_portal.services;

import uk.ac.leedsbeckett.Student_portal.model.Student;
import java.util.List;

public interface StudentService {
    Student createStudent(Student student);
    Student getStudentById(long  studentId);
    Student getStudentByExternalStudentId(String externalStudentId);
    List<Student> getAllStudents();
    Student updateStudentByExternalStudentId(String externalStudentId, Student updatedStudent);
    void deleteStudentByExternalStudentId(String externalStudentId);
}


