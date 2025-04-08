package uk.ac.leedsbeckett.Student_portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.leedsbeckett.Student_portal.model.Course;
import uk.ac.leedsbeckett.Student_portal.model.Student;
import uk.ac.leedsbeckett.Student_portal.services.CourseService;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping(value="/courses", method = {RequestMethod.GET, RequestMethod.PUT,RequestMethod.POST,RequestMethod.DELETE})
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> CourseList = courseService.getAllCourses();
        return new ResponseEntity<>(CourseList, HttpStatus.OK);
    }
    @PostMapping("/create_course")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course createdCourse = courseService.createCourse(course);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    @PostMapping("/{username}/{courseId}/enroll")
    public ResponseEntity<String> enrollStudent(
            @PathVariable String username,
            @PathVariable Long courseId) {
        String response = courseService.enrollStudentInCourse(username,courseId);
        return ResponseEntity.ok(response);
    }
}
