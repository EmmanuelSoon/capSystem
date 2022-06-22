package team2.capSystem.exceptions;

public class ClassFullException extends Exception {

    public ClassFullException() {
        super("Unable to enroll, class is full!");
    }
    
    public ClassFullException(String message) {
        super(message);
    }

    public ClassFullException(String message, Throwable cause) {
        super(message, cause);
    }
}
