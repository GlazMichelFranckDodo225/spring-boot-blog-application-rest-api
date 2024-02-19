package com.dgmf.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PostDto {
    private Long id;
    @NotEmpty @Size(min = 2, message = "Post Title Should Have at " +
            "Least 2 Characters")
    private String title;
    @NotEmpty @Size(min = 10, message = "Post Description Should Have at " +
            "Least 10 Characters")
    private String description;
    @NotEmpty
    private String content;
    private Set<CommentDto> comments;
}
