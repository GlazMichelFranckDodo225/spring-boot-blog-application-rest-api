package com.dgmf.service;

import com.dgmf.dto.PostDto;
import com.dgmf.dto.PostResponseDto;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponseDto getAllPosts(
            int pageNo, int pageSize, String sortBy, String sortDir
    );

    PostDto getPostById(Long postId);
    PostDto updatePost(PostDto postDto, Long postId);
    void deletePostById(Long postId);
}
