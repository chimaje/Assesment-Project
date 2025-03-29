package uk.ac.leedsbeckett.Student_portal.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(String message) {
        super(message);
    }
  public CourseNotFoundException(String message, Throwable th) {

    super(message, th);
  }

  public CourseNotFoundException(Long courseId) {
  }
}



