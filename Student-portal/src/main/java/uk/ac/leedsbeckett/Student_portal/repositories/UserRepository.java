package uk.ac.leedsbeckett.Student_portal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import uk.ac.leedsbeckett.Student_portal.model.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User , Long>{
    Optional<User> findByUsernameAndPassword(String username, String password);
}
