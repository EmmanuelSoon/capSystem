package team2.capSystem.exceptions;

public class GpaExistException extends Exception{

    public GpaExistException(String message) {
        super(message);
    }

    public GpaExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
