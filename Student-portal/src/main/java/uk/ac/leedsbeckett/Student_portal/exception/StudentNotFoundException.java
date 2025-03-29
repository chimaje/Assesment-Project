package uk.ac.leedsbeckett.Student_portal.exception;


public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String message) {

        super(message);
    }

    public StudentNotFoundException(String message, Throwable th) {

        super(message, th);
    }
}
