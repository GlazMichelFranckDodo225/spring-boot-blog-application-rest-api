package com.dgmf.service.impl;

import com.dgmf.exception.ResourceNotFoundException;
import com.dgmf.entity.Post;
import com.dgmf.repository.PostRepository;
import com.dgmf.service.PostService;
import com.dgmf.web.dto.PostDto;
import com.dgmf.web.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    /*@Autowired // Constructor-based Dependency Injection
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }*/

    @Override
    public PostDto createPost(PostDto postDto) {
        // Convert PostDto to Post
        Post post = mapDtoToEntity(postDto);

        // Save Post
        Post savedPost = postRepository.save(post);

        // Convert savedPost to PostDto
        PostDto savedPostDto = mapEntityToDto(savedPost);

        return savedPostDto;
    }

    @Override
    public PostResponse getAllPosts(
            int pageNo, int pageSize, String sortBy, String sortDir
    ) {
        // Create a Sort Object
        Sort sort =
                sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                        Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        // Create Pageable instance
        // Pageable pageable = PageRequest.of(pageNo, pageSize);
        // Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        // Get Content for Page Object
        List<Post> listOfPosts = posts.getContent();

        // Convert List of Post to List of PostDto
        List<PostDto> content = listOfPosts.stream()
                .map(post -> mapEntityToDto(post))
                .collect(Collectors.toList());

        PostResponse postResponse = PostResponse.builder()
                .content(content)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalElements(posts.getTotalElements())
                .totalPages(posts.getTotalPages())
                .last(posts.isLast())
                .build();

        return postResponse;
    }

    @Override
    public PostDto getPostById(Long postDtoId) {
        // Get Post By Id from the Database
        Post post = postRepository
                .findById(postDtoId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Post",
                                "Id",
                                postDtoId
                        )
                );

        PostDto postDto = mapEntityToDto(post);

        return postDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long postId) {
        // Get Post By Id from the Database
        Post existingPost = postRepository
                .findById(postId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Post",
                                "Id",
                                postId
                        )
                );

        existingPost.setTitle(postDto.getTitle());
        existingPost.setDescription(postDto.getDescription());
        existingPost.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(existingPost);

        return mapEntityToDto(updatedPost);
    }

    @Override
    public void deletePostById(Long postId) {
        // Get Post By Id from the Database
        Post post = postRepository
                .findById(postId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Post",
                                "Id",
                                postId
                        )
                );

        postRepository.delete(post);
    }

    private PostDto mapEntityToDto(Post post) {
        // Convert Post into PostDto
        PostDto postDto = PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .content(post.getContent())
                .build();

        return postDto;
    }

    private Post mapDtoToEntity(PostDto postDto) {
        // Convert PostDto to Post
        Post post = Post.builder()
                .title(postDto.getTitle())
                .description(postDto.getDescription())
                .content(postDto.getContent())
                .build();

        return post;
    }
}