package team2.capSystem.exceptions;

public class AfterTwoWeekUnenrollmentException extends  Exception{
    public AfterTwoWeekUnenrollmentException(String message) {
        super(message);
    }

    public AfterTwoWeekUnenrollmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
