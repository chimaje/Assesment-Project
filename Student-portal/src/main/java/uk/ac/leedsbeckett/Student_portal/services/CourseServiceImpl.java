package uk.ac.leedsbeckett.Student_portal.services;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.leedsbeckett.Student_portal.exception.CourseNotFoundException;
import uk.ac.leedsbeckett.Student_portal.exception.StudentNotFoundException;
import uk.ac.leedsbeckett.Student_portal.model.Course;
import uk.ac.leedsbeckett.Student_portal.model.Student;
import uk.ac.leedsbeckett.Student_portal.repositories.CourseRepository;
import uk.ac.leedsbeckett.Student_portal.repositories.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository repository;
    private final StudentRepository studentRepository;
    private final IntegrationService integrationService;

    public CourseServiceImpl(CourseRepository repository, StudentRepository studentRepository, IntegrationService integrationService) {
        this.repository = repository;
        this.studentRepository = studentRepository;
        this.integrationService = integrationService;
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
    public String enrollStudentInCourse(String externalStudentId, Long courseId) {
        // 1. Find student and course
        Student student = studentRepository.findByExternalStudentId(externalStudentId)
                .orElseThrow(() -> new StudentNotFoundException(externalStudentId));
        Course course = repository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        // 2. Process enrollment
        student.enrolInCourse(course);
        studentRepository.save(student);

        // 3. Call Finance Service
        String invoiceRef = integrationService.createInvoice(
                student.getExternalStudentId(),
                course.getFee()
        );
        return String.format("Successfully enrolled in %s. Invoice Reference: %s",
                course.getTitle(), invoiceRef);
//        return "Enrolled. Invoice Ref: " + invoiceRef;
    }
//    public String enrollStudentInCourse(String externalStudentId, Long courseId) {
//        Optional<Student> studentOpt = studentRepository.findByExternalStudentId(externalStudentId);
//        Optional<Course> courseOpt = repository.findById(courseId);
//
//        if (studentOpt.isPresent() && courseOpt.isPresent()) {
//            Student student = studentOpt.get();
//            Course course = courseOpt.get();
//
//            if (student.hasEnrolledInCourse(course)) {
//                return "Student is already enrolled in this course.";
//            }
//
//            student.enrolInCourse(course);
//            studentRepository.save(student);
//
//            // Call finance service to generate an invoice
////            integrationService.createInvoice(student.getExternalStudentId(), course.getFee());
////            String referenceNumber = integrationService.createInvoice(student.getExternalStudentId(), course.getFee());
//                String referenceNumber = integrationService.createInvoice(
//                        student.getExternalStudentId(),
//                        course.getFee()
//                );
////            return "Enrollment successful, and invoice created.";
//            return "Enrollment successful! Invoice reference number: " + referenceNumber;
//        }
//
//        return "Student or Course not found.";
//    }
}

