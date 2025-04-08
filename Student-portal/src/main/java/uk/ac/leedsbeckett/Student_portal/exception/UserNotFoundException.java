package uk.ac.leedsbeckett.Student_portal.exception;

public class UserNotFoundException extends RuntimeException {
    // Add serialVersionUID for serialization compatibility
    private static final long serialVersionUID = 1L;

    // Constructor with message
    public UserNotFoundException(String message) {
        super(message);
    }

    // Constructor with message and cause
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    // Additional constructor with just cause
    public UserNotFoundException(Throwable cause) {
        super(cause);
    }
}