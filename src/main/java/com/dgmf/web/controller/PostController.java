package com.dgmf.web.controller;

import com.dgmf.service.PostService;
import com.dgmf.web.dto.PostDto;
import com.dgmf.web.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // Create Post REST API
    @PostMapping
    public ResponseEntity<PostDto> createPost(
            @RequestBody PostDto postDto
    ) {
        return new ResponseEntity<>(
                postService.createPost(postDto),
                HttpStatus.CREATED
        );
    }

    // Get All Posts REST API
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(
                    value = "pageNo",
                    defaultValue = "0",
                    required = false
            ) int pageNo,
            @RequestParam(
                    value = "pageSize",
                    defaultValue = "10",
                    required = false
            ) int pageSize
    ) {
        return postService.getAllPosts(pageNo, pageSize);
    }

    // Get Post By Id REST API
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(
            @PathVariable("id") Long postDtoId
    ) {
        return ResponseEntity
                .ok(postService.getPostById(postDtoId));
    }

    // Update Post By Id REST API
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(
            @RequestBody PostDto postDto,
            @PathVariable("id") Long postDtoId
        ) {
        return ResponseEntity.ok(postService.updatePost(postDto, postDtoId));
    }

    // Delete Post By Id REST API
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(
            @PathVariable("id") Long postId) {
        postService.deletePostById(postId);

        return ResponseEntity.ok("Post Entity Deleted Successfully");
    }
}
