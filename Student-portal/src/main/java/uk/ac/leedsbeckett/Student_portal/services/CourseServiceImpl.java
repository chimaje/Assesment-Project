package uk.ac.leedsbeckett.Student_portal.services;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.leedsbeckett.Student_portal.exception.CourseNotFoundException;
import uk.ac.leedsbeckett.Student_portal.exception.StudentNotFoundException;
import uk.ac.leedsbeckett.Student_portal.exception.UserNotFoundException;
import uk.ac.leedsbeckett.Student_portal.model.Course;
import uk.ac.leedsbeckett.Student_portal.model.Student;
import uk.ac.leedsbeckett.Student_portal.model.User;
import uk.ac.leedsbeckett.Student_portal.repositories.CourseRepository;
import uk.ac.leedsbeckett.Student_portal.repositories.StudentRepository;
import uk.ac.leedsbeckett.Student_portal.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository repository;
    private final StudentRepository studentRepository;
    private final IntegrationService integrationService;
    private final UserRepository userRepository;

    public CourseServiceImpl(CourseRepository repository, StudentRepository studentRepository, IntegrationService integrationService ,UserRepository userRepository) {
        this.repository = repository;
        this.studentRepository = studentRepository;
        this.integrationService = integrationService;
        this.userRepository = userRepository;
    }

    @Override
    public Course createCourse(Course course) {
        return repository.save(course);
    }

    @Override
    public Course getCourseById(long courseId) {
        return null;
    }

    @Override
    public List<Course> getAllCourses() {
        return repository.findAll();
    }

    @Override
    public void deleteCourse(Course course) {

    }

    @Transactional
    public String enrollStudentInCourse(Long userId,Long courseId) {


        // 1. Find student and course
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User does not exist"));

        // Create student if not exists
        Student student = user.getStudent();
        if (student == null) {
            student = new Student();
            student.setUser(user);
            user.setStudent(student);
            student = studentRepository.save(student);
        }
        Course course = repository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        // 2. Process enrollment
        student.enrolInCourse(course);
        studentRepository.save(student);

//        // 3. Call Finance Service
//        String invoiceRef = integrationService.createInvoice(
//                student.getExternalStudentId(),
//                course.getFee()
//        );
//        return String.format("Successfully enrolled in %s. Invoice Reference: %s",
//                course.getTitle(), invoiceRef);
       return "Enrolled.s" ;
    }
}