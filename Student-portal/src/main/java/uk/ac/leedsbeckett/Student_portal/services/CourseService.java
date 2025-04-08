package uk.ac.leedsbeckett.Student_portal.services;

import uk.ac.leedsbeckett.Student_portal.model.Course;

import java.util.List;

public interface CourseService {
    Course createCourse(Course course);
    Course getCourseById(long courseId);
    List<Course> getAllCourses( );
    void deleteCourse(Course course);
    String enrollStudentInCourse(Long userId, Long courseId);
}
