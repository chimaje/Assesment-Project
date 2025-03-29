package uk.ac.leedsbeckett.Student_portal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.leedsbeckett.Student_portal.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
