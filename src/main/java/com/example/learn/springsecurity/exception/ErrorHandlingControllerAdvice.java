package com.example.learn.springsecurity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler
    public ProblemDetail handle(UserNotFoundException ex) {
        return ProblemDetail
                .forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler
    public ProblemDetail handle(UserEmailExistsException ex) {
        return ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // Handle any other Exception as 500 Internal server error
    /*
    @ExceptionHandler
    public ProblemDetail handleException(Exception ex) {
        String errorMsg = (ex.getMessage() != null) ? "Error occurred: " + ex.getMessage() : "An unexpected error occurred";

        return ProblemDetail
                .forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred: " + errorMsg);
    }

     */
}
