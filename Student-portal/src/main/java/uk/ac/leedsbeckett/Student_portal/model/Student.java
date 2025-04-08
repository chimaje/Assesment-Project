package uk.ac.leedsbeckett.Student_portal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Data
@Entity
@EnableAutoConfiguration
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "external_student_id", unique = true, nullable = false, updatable = false)
    private String externalStudentId;

    private String surname;
    private String forename;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_student",
            joinColumns ={ @JoinColumn(name = "user_id", referencedColumnName = "id") },
            inverseJoinColumns ={ @JoinColumn(name = "student_id", referencedColumnName = "id") }
    )

    private User user;

    // Bi-directional relationship: Many-to-many with courses
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude  // Serialize this side
    private Set<Course> courseEnrolledIn = new HashSet<>();
    
//    @JsonManagedReference
//    public Set<Course> getCourse(){
//        return courseEnrolledIn;
//    }

    @PrePersist
    private void generateExternalId() {
        if (this.externalStudentId == null) {
            // Generate random 7-digit number
            int randomNum = ThreadLocalRandom.current().nextInt(1_000_000, 10_000_000);
            this.externalStudentId = "c" + randomNum;
        }
    }
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
