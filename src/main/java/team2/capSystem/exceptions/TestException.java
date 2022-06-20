package team2.capSystem.exceptions;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;


public class TestException {
    private final String message;
    private final HttpStatus HttpStatus;
    private final ZonedDateTime timestamp;

    public TestException(String message, HttpStatus httpStatus, ZonedDateTime timestamp) {
        this.message = message;
        HttpStatus = httpStatus;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    


    
}
