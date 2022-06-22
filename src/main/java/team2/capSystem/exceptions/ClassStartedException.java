package team2.capSystem.exceptions;

public class ClassStartedException extends Exception{
    
    public ClassStartedException(){
        super("Unable to enroll, class has already started!");
    }
    
    public ClassStartedException(String message) {
        super(message);
    }

    public ClassStartedException(String message, Throwable cause) {
        super(message, cause);
    }
}
