package com.dgmf.controller;

import com.dgmf.service.CommentService;
import com.dgmf.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(
            @PathVariable("postId") Long postId
    ) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId
    ) {
        return ResponseEntity.ok(
                commentService.getCommentById(
                    postId,
                    commentId
                )
            );
    }

    @PutMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentDto commentDto
    ) {
        return ResponseEntity.ok(commentService.updateComment(
                postId, commentId, commentDto
            )
        );
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId
    ) {
        commentService.deleteCommentById(postId, commentId);

        return new ResponseEntity<>(
                "Comment Deleted Successfully", HttpStatus.OK
        );
    }

}
