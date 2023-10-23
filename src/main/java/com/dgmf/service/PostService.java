package com.dgmf.service;

import com.dgmf.entity.Post;
import com.dgmf.web.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    List<PostDto> getAllPosts();

    PostDto getPostById(Long postId);
    PostDto updatePost(PostDto postDto, Long postId);
}
