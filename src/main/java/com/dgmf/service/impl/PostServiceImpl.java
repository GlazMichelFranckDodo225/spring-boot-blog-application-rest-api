package com.dgmf.service.impl;

import com.dgmf.web.dto.PostDtoRequest;
import com.dgmf.web.dto.PostDtoResponse;
import com.dgmf.entity.Post;
import com.dgmf.repository.PostRepository;
import com.dgmf.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        Post post = mapDtoToEntity(postDtoRequest);

        // Save Post
        Post savedPost = postRepository.save(post);

        // Convert savedPost to PostDtoResponse
        PostDtoResponse postDtoResponse = mapEntityToDto(savedPost);

        return postDtoResponse;
    }

    @Override
    public List<PostDtoResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();

        // Convert List of Post to List of PostDtoRequest
        List<PostDtoResponse> postDtoResponses = posts.stream()
                .map(post -> mapEntityToDto(post))
                .collect(Collectors.toList());

        return postDtoResponses;
    }

    private PostDtoResponse mapEntityToDto(Post post) {
        // Convert Post into PostDtoResponse
        PostDtoResponse postDtoResponse = PostDtoResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .content(post.getContent())
                .build();

        return postDtoResponse;
    }

    private Post mapDtoToEntity(PostDtoRequest postDtoRequest) {
        // Convert PostDtoRequest to Post
        Post post = Post.builder()
                .title(postDtoRequest.getTitle())
                .description(postDtoRequest.getDescription())
                .content(postDtoRequest.getContent())
                .build();

        return post;
    }
}