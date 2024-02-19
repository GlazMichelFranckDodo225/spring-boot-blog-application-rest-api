package com.dgmf.controller;

import com.dgmf.service.PostService;
import com.dgmf.utils.AppConstants;
import com.dgmf.dto.PostDto;
import com.dgmf.dto.PostResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // Create Post REST API
    @PostMapping
    public ResponseEntity<PostDto> createPost(
            @Valid @RequestBody PostDto postDto
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
                    defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false
            ) int pageNo,
            @RequestParam(
                    value = "pageSize",
                    defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false
            ) int pageSize,
            @RequestParam(
                    value = "sortBy",
                    defaultValue = AppConstants.DEFAULT_SORT_BY,
                    required = false
            ) String sortBy,
            @RequestParam(
                    value = "sortDir",
                    defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                    required = false
            ) String sortDir
    ) {
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
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
            @Valid @RequestBody PostDto postDto,
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
