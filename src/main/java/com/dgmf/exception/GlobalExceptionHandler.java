package com.dgmf.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

// Class Responsible to Handle Specific (ResourceNotFoundException, BlogAPIException)
// and Global Exceptions
@ControllerAdvice
public class GlobalExceptionHandler {
    // Specific "ResourceNotFoundException" Exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(
            ResourceNotFoundException resourceNotFoundException,
            WebRequest webRequest // To Send Back Details to the Client
    ) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(new Date())
                .message(resourceNotFoundException.getMessage())
                // False ==> To Send Back Only the URI instead of the
                // Full Description
                .details(webRequest.getDescription(false))
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // Specific "BlogAPIException" Exception
    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<ErrorDetails> handleBlogAPIException(
            BlogAPIException blogAPIException,
            WebRequest webRequest // To Send Back Details to the Client
    ) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(new Date())
                .message(blogAPIException.getMessage())
                // False ==> To Send Back Only the URI instead of the
                // Full Description
                .details(webRequest.getDescription(false))
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // Global Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(
            Exception exception,
            WebRequest webRequest // To Send Back Details to the Client
    ) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(new Date())
                .message(exception.getMessage())
                // False ==> To Send Back Only the URI instead of the
                // Full Description
                .details(webRequest.getDescription(false))
                .build();

        return new ResponseEntity<>(
                errorDetails, HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
