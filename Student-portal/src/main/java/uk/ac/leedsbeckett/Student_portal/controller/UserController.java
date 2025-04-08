package uk.ac.leedsbeckett.Student_portal.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import uk.ac.leedsbeckett.Student_portal.model.Student;
import uk.ac.leedsbeckett.Student_portal.model.User;
import uk.ac.leedsbeckett.Student_portal.services.UserService;
import uk.ac.leedsbeckett.Student_portal.repositories.UserRepository;

import java.util.List;


@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    private final UserService userService ;

    public UserController( UserService userService){
        this.userService = userService;
    }
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping
    public ResponseEntity<List<User>> getUsers(){
        List<User> userList = userService.getallUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserbyId(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @PostMapping(value="/create",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@RequestBody User user){
        User createUser = userService.createUser(user);
        return new ResponseEntity<>(createUser, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User loginRequest) {
        User user = userService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(user);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAllUser() {
        userService.deleteAllUser();
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
