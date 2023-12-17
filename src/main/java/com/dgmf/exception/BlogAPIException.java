package com.dgmf.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

// We throw this Exception whenever we write some
// Business Logic or Validate Request Parameters
@Data
public class BlogAPIException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;

    public BlogAPIException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public BlogAPIException(String message, HttpStatus httpStatus, String message1) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message1;
    }
}
