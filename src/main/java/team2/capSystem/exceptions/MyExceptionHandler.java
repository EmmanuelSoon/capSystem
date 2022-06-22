package team2.capSystem.exceptions;

import java.time.ZoneId;
import java.time.ZonedDateTime;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = {RequestException.class})
    public ResponseEntity<Object> handleRequestException(RequestException e) {
        // payload containing exception details
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        TestException testException = new TestException(
            e.getMessage(),
            badRequest,
            ZonedDateTime.now(ZoneId.of("Z")));
        //return response entity
        return new ResponseEntity<Object>(testException, badRequest);

    }

    @ExceptionHandler(value = {ClassFullException.class})
    public String handleClassFullException(ClassFullException e, Model model) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        TestException testException = new TestException(
            e.getMessage(),
            badRequest,
            ZonedDateTime.now(ZoneId.of("Z")));
        model.addAttribute("error", testException);
        return "redirect:/handleExceptionPage";
    }

    @ExceptionHandler(value = {ClassStartedException.class})
    public String handleClassStartedException(ClassStartedException e, Model model) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        TestException testException = new TestException(
            e.getMessage(),
            badRequest,
            ZonedDateTime.now(ZoneId.of("Z")));
            model.addAttribute("error", testException);
            return "redirect:/handleExceptionPage";
    }


}
 