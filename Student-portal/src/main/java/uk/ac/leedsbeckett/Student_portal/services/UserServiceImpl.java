package uk.ac.leedsbeckett.Student_portal.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.ac.leedsbeckett.Student_portal.exception.UserNotFoundException;
import uk.ac.leedsbeckett.Student_portal.model.User;
import uk.ac.leedsbeckett.Student_portal.repositories.UserRepository;

import java.util.List;


@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository){
        this.repository =repository;
    }


    @Override
    public User createUser(User user) {
        return repository.save(user);
    }
    @Override
    public User authenticate(String username, String password) {
        log.info("Authentication attempt - Username: {}, Password: {}", username, password);
        return repository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new RuntimeException(
                        String.format("Invalid credentials for username: %s and password: %s", username, password)
                ));
//        return repository.findByUsernameAndPassword(username, password)
//                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }
    @Override
    public User getUserById(long userId) {
        return repository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found " + userId));
    }

    @Override
    public List<User> getallUsers() {
        return repository.findAll();
    }
    @Override
    public User getUserByUsername(String username){
        return repository.findByUsername(username).orElseThrow(()-> new UserNotFoundException("User not found" + username));
    }

    @Override
    public User updateUserId(long userId, User updatedUser) {
        if (!repository.existsById(userId)) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
//        //Add additional validation logic if required
//        updatedStudent.setStudentId(studentId);
        return repository.save(updatedUser);
    }

    @Override
    public void deleteUserId(long userId) {
        if (!repository.existsById(userId)) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
        repository.deleteById(userId);

    }

    @Override
    public void deleteAllUser() {
        repository.deleteAll();
    }
}
