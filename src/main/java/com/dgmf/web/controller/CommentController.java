package com.dgmf.web.controller;

import com.dgmf.service.CommentService;
import com.dgmf.web.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @PathVariable("postId") Long postId,
            @RequestBody CommentDto commentDto
    ) {
       return new ResponseEntity<>(
               commentService.createComment(postId, commentDto),
               HttpStatus.CREATED
       );
    }
}
