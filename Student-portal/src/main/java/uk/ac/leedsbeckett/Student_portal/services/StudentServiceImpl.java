package uk.ac.leedsbeckett.Student_portal.services;

import org.springframework.stereotype.Service;
import uk.ac.leedsbeckett.Student_portal.model.Student;
import uk.ac.leedsbeckett.Student_portal.exception.StudentNotFoundException;
import uk.ac.leedsbeckett.Student_portal.model.User;
import uk.ac.leedsbeckett.Student_portal.repositories.StudentRepository;

import java.util.List;
import java.util.Optional;

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
    public void deleteAllStudents() {
        repository.deleteAll();
    }

    @Override
    public Student updateStudentByExternalStudentId(String studentId, Student updatedStudent) {
        Student existingStudent = repository.findByExternalStudentId(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + studentId));
        existingStudent.setSurname(updatedStudent.getSurname());
        existingStudent.setForename(updatedStudent.getForename());
        return repository.save(existingStudent);
    }

    public List<Student> getAllStudents() {

        return repository.findAll();
    }
    public Optional<Student> getStudentByUser(User user){
        return repository.findByUser(user);
    }
}