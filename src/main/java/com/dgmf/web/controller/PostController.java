package com.dgmf.web.controller;

import com.dgmf.web.dto.PostDtoRequest;
import com.dgmf.web.dto.PostDtoResponse;
import com.dgmf.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // Create Post REST API
    @PostMapping
    public ResponseEntity<PostDtoResponse> createPost(
            @RequestBody PostDtoRequest postDtoRequest
    ) {
        return new ResponseEntity<>(
                postService.createPost(postDtoRequest),
                HttpStatus.CREATED
        );
    }

    // Get All Posts REST API
    @GetMapping
    public ResponseEntity<List<PostDtoResponse>> getAllPosts() {
        return ResponseEntity
                .ok()
                .body(postService.getAllPosts());
    }
}
