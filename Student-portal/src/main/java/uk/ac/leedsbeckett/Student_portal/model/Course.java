package uk.ac.leedsbeckett.Student_portal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EnableAutoConfiguration
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private double fee;

    // Map the relationship using 'mappedBy' in Course to ensure consistency.
    @JsonIgnore
    @ManyToMany(mappedBy = "courseEnrolledIn", fetch = FetchType.LAZY)
    private Set<Student> students = new HashSet<>();

    // No-arg constructor required by JPA (public or protected)
    public Course() {
        // JPA requires a no-arg constructor
    }

    // Constructor for initializing a Course object with data
    public Course(String title, String description, double fee) {
        this.title = title;
        this.description = description;
        this.fee = fee;
    }

    // Optional: Custom toString() method
    @Override
    public String toString() {
        return "Course{id=" + id + ", title='" + title + "', description='" + description + "', fee=" + fee + "}";
    }
}
