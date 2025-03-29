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

@SpringBootApplication
@RestController
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

    @PostMapping("/{courseId}/enroll/{externalStudentId}")
    public ResponseEntity<String> enrollStudent(
            @PathVariable String externalStudentId,
            @PathVariable Long courseId) {
        String response = courseService.enrollStudentInCourse(externalStudentId, courseId);
        return ResponseEntity.ok(response);
    }
}
