package uk.ac.leedsbeckett.Student_portal.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.leedsbeckett.Student_portal.model.Student;
import uk.ac.leedsbeckett.Student_portal.services.StudentService;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StudentControllerTest {
    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;
    private Student student;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        student = new Student("c875902","Agada","Chimaje");
    }
    @Test
    void createStudent() {
        //arrange
        when(studentService.createStudent(student)).thenReturn(student);
        //act
        ResponseEntity<Student> response = studentController.createStudent(student);
        //assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(student, response.getBody());
        verify(studentService,times(1)).createStudent(student);
    }

    @Test
    void getStudentByExternalStudentId() {
        when(studentService.getStudentByExternalStudentId("c1754369")).thenReturn(student);
        ResponseEntity<Student> response = studentController.getStudentByExternalStudentId("c1754369");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(student, response.getBody());

    }

    @Test
    void getStudents() {
        List<Student> studentList = List.of(student); // Create a list with a single student
        when(studentService.getAllStudents()).thenReturn(studentList); // Return a List<Student>

        ResponseEntity<List<Student>> response = studentController.getStudents();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(studentList, response.getBody()); // Compare with the expected list
    }


}