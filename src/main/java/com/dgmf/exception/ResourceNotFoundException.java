package com.dgmf.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
// This Annotation causes Spring Boot to respond with the
// specified HTTP Status Code whenever this Exception is
// thrown from the Controller
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private Long fieldValue;

    public ResourceNotFoundException(
            String resourceName,
            String fieldName,
            Long fieldValue
    ) {
        // Post Not Found with id : 1
        super(String.format("%s Not Found with %s %s",
                resourceName,
                fieldName,
                fieldValue)
        );

        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
