package uk.ac.leedsbeckett.Student_portal.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import uk.ac.leedsbeckett.Student_portal.exception.UserNotFoundException;
import uk.ac.leedsbeckett.Student_portal.model.Course;
import uk.ac.leedsbeckett.Student_portal.model.Student;
import uk.ac.leedsbeckett.Student_portal.model.User;
import uk.ac.leedsbeckett.Student_portal.services.StudentService;
import uk.ac.leedsbeckett.Student_portal.repositories.StudentRepository;
import uk.ac.leedsbeckett.Student_portal.services.IntegrationService;
import uk.ac.leedsbeckett.Student_portal.services.UserService;

import java.util.*;

@SpringBootApplication
@RestController
@RequestMapping("/students")
public class StudentController{
    private final StudentService studentService;
    private final IntegrationService integrationService;
    private final UserService userService;

    public StudentController(StudentService studentService ,IntegrationService integrationService , UserService userService) {
        this.studentService = studentService;
        this.integrationService = integrationService;
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        integrationService.registerStudent(student);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);

    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentByExternalStudentId(@PathVariable String studentId) {
        Student student = studentService.getStudentByExternalStudentId(studentId);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getStudents() {
        List<Student> studentList = studentService.getAllStudents();
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }
    @GetMapping("/user/{username}")
    public ResponseEntity<StudentResponse> getStudentByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        Student student = studentService.getStudentByUser(user)
                .orElseThrow(() -> new UserNotFoundException("Student not found for user: " + username));

        return ResponseEntity.ok(mapToStudentResponse(student));
    }

    private StudentResponse mapToStudentResponse(Student student) {
        return new StudentResponse(
                student.getExternalStudentId(),
                student.getSurname(),
                student.getForename(),
                student.getCourseEnrolledIn()
        );
    }

    // Response DTO
    public record StudentResponse(
            String externalStudentId,
            String surname,
            String forename,
            Set<Course> courses
    ) {}

    @PutMapping("/{studentId}")
    public ResponseEntity<Student> updateStudentByExternalStudentId(
            @PathVariable String studentId,
            @RequestBody Student updatedStudent) {
        Student student = studentService.updateStudentByExternalStudentId(studentId, updatedStudent);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable String studentId) {
        studentService.deleteStudentByExternalStudentId(studentId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAllStudents() {
        studentService.deleteAllStudents();
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
