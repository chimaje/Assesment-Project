package uk.ac.leedsbeckett.Student_portal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@Data
@Entity
@EnableAutoConfiguration
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String Email;

    private String surname;
    private String forename;

    public User(String username , String email , String Surname , String Forename,String password){
        this.username = username;
        this.Email = email;
        this.surname = Surname;
        this.forename = Forename;
        this.password = password;
    }
    public User(){

    }
    @OneToOne(mappedBy = "user")
    @JsonBackReference("student-user")
    @ToString.Exclude
    private Student student;


}
