package uk.ac.leedsbeckett.Student_portal.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import uk.ac.leedsbeckett.Student_portal.model.Student;
import uk.ac.leedsbeckett.Student_portal.services.StudentService;
import uk.ac.leedsbeckett.Student_portal.repositories.StudentRepository;
import uk.ac.leedsbeckett.Student_portal.services.IntegrationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SpringBootApplication
@RestController
@RequestMapping("/students")
public class StudentController{
    private final StudentService studentService;
    private final IntegrationService integrationService;

    public StudentController(StudentService studentService ,IntegrationService integrationService) {
        this.studentService = studentService;
        this.integrationService = integrationService;
    }

  private final RestTemplate restTemplate = new RestTemplate();
//    private final Map<String, Student> studentDatabase = new HashMap<>();

    @PostMapping("/register")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        integrationService.registerStudent(student);
//        String libraryUrl = "http://localhost/api/register";
////        String financeUrl = "http://localhost:8081/accounts/register";
//       IntegrationService.LibraryAccountRequest request = new IntegrationService.LibraryAccountRequest(student.getExternalStudentId());
////       IntegrationService.FinanceAccountRequest request2 = new IntegrationService.FinanceAccountRequest(student.getExternalStudentId());
//        restTemplate.postForObject(libraryUrl, request, Void.class);
////        restTemplate.postForObject(financeUrl, request2, Void.class);
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

    @PutMapping("/{studentId}")
    public ResponseEntity<Student> updateStudentById(
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
