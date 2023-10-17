package com.dgmf.service.impl;

import com.dgmf.web.dto.PostDtoRequest;
import com.dgmf.web.dto.PostDtoResponse;
import com.dgmf.entity.Post;
import com.dgmf.repository.PostRepository;
import com.dgmf.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    /*@Autowired // Constructor-based Dependency Injection
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }*/

    @Override
    public PostDtoResponse createPost(PostDtoRequest postDtoRequest) {
        // Convert PostDtoRequest to Post
        Post post = Post.builder()
                .title(postDtoRequest.getTitle())
                .description(postDtoRequest.getDescription())
                .content(postDtoRequest.getContent())
                .build();

        // Save Post
        Post savedPost = postRepository.save(post);

        // Convert savedPost to PostDtoResponse
        PostDtoResponse postDtoResponse = PostDtoResponse.builder()
                .id(savedPost.getId())
                .title(savedPost.getTitle())
                .description(savedPost.getDescription())
                .content(savedPost.getContent())
                .build();

        return postDtoResponse;
    }
}