package team2.capSystem.exceptions;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.util.ClassUtil.Ctor;

public class TestException {
    private final String message;
    private final Throwable throwable;
    private final HttpStatus HttpStatus;
    private final ZonedDateTime timestamp;

    public TestException(String message, Throwable throwable, org.springframework.http.HttpStatus httpStatus,
            ZonedDateTime timestamp) {
        this.message = message;
        this.throwable = throwable;
        HttpStatus = httpStatus;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    


    
}
