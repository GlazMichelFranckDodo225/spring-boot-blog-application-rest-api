package com.dgmf.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;
}
