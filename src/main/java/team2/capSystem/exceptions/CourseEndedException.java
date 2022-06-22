package team2.capSystem.exceptions;

public class CourseEndedException extends Exception{
    public CourseEndedException(String message) {
        super(message);
    }

    public CourseEndedException(String message, Throwable cause) {
        super(message, cause);
    }
}
