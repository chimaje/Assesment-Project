package uk.ac.leedsbeckett.Student_portal.services;

import org.springframework.stereotype.Service;
import uk.ac.leedsbeckett.Student_portal.model.Student;
import uk.ac.leedsbeckett.Student_portal.exception.StudentNotFoundException;
import uk.ac.leedsbeckett.Student_portal.repositories.StudentRepository;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {

        this.repository = repository;
    }

    @Override
    public Student createStudent(Student student) {
        //Add additional validation logic if required
        return repository.save(student);
    }

    @Override
    public Student getStudentById(long studentId) {
        return repository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + studentId));
    }
    @Override
    public Student getStudentByExternalStudentId(String externalStudentId) {
        return repository.findByExternalStudentId(externalStudentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + externalStudentId));
    }

    @Override
    public void deleteStudentByExternalStudentId(String studentId) {
        if (!repository.existsById(Long.valueOf(studentId))) {
            throw new StudentNotFoundException("Student not found with ID: " + studentId);
        }
        repository.deleteById(Long.valueOf(studentId));
    }

    @Override
    public Student updateStudentByExternalStudentId(String studentId, Student updatedStudent) {
        if (!repository.existsById(Long.valueOf(studentId))) {
            throw new StudentNotFoundException("Student not found with ID: " + studentId);
        }
//        //Add additional validation logic if required
//        updatedStudent.setStudentId(studentId);
        return repository.save(updatedStudent);
    }

    public List<Student> getAllStudents() {

        return repository.findAll();
    }
}