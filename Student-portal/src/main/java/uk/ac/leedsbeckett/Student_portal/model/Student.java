package uk.ac.leedsbeckett.Student_portal.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EnableAutoConfiguration
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(unique = true)
    private String externalStudentId;

    private String surname;
    private String forename;

    // Bi-directional relationship: Many-to-many with courses
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Course> courseEnrolledIn = new HashSet<>();

    // No-arg constructor required by JPA (public or protected)
    public Student() {
        // JPA requires a no-arg constructor
    }

    // Constructor for initializing a Student object with data
    public Student(String externalStudentId, String surname, String forename) {
        this.externalStudentId = externalStudentId;
        this.surname = surname;
        this.forename = forename;
        this.courseEnrolledIn = new HashSet<>();
    }

    // Enroll the student in a course (ensures no duplicates)
    public void enrolInCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (courseEnrolledIn == null) {
            courseEnrolledIn = new HashSet<>();
        }
        if (!courseEnrolledIn.contains(course)) {
            courseEnrolledIn.add(course);
        }
    }

    // Remove the course from the student's enrolled courses
    public void removeCourse(Course course) {
        if (courseEnrolledIn != null) {
            courseEnrolledIn.remove(course);
        }
    }

    // Check if the student is already enrolled in the course
    public boolean hasEnrolledInCourse(Course course) {
        return courseEnrolledIn != null && courseEnrolledIn.contains(course);
    }

    // Optional: Custom toString() method
    @Override
    public String toString() {
        return "Student{id=" + id + ", externalStudentId='" + externalStudentId + "', surname='" + surname + "', forename='" + forename + "'}";
    }
}
