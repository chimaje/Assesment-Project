//package uk.ac.leedsbeckett.Student_portal;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class StudentPortalApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(StudentPortalApplication.class, args);
//	}
//
//}
//
package uk.ac.leedsbeckett.Student_portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class StudentPortalApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(StudentPortalApplication.class);
		Map<String, Object> config = new HashMap<>();
		config.put("server.port", 8083);  // Set the port programmatically
		app.setDefaultProperties(config);
		app.run(args);
	}
}