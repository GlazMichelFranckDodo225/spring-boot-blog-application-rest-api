package com.dgmf.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CommentDto {
    private Long id;
    @NotEmpty(message = "Name Should Not Be Null or Empty")
    private String name;
    @NotEmpty(message = "Email Should Not Be Null or Empty")
    @Email
    private String email;
    @NotEmpty
    @Size(min = 10, message = "Comment Body Must Be Minimum 10 Characters")
    private String body;
}
