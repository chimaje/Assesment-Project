package uk.ac.leedsbeckett.Student_portal.services;

import java.util.List;
import uk.ac.leedsbeckett.Student_portal.model.User;

public interface UserService {
    User createUser(User user);

    User authenticate(String username, String password);

    User getUserById(long  userId);
    List<User> getallUsers();
    User updateUserId(long userId, User updatedUser);
    void deleteUserId(long userId);
    void deleteAllUser();
}
