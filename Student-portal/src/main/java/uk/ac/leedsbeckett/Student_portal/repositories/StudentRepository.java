package uk.ac.leedsbeckett.Student_portal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import uk.ac.leedsbeckett.Student_portal.model.Student;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByExternalStudentId(String externalStudentId);
}
