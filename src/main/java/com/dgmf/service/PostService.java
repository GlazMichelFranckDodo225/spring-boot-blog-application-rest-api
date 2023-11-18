package com.dgmf.service;

import com.dgmf.entity.Post;
import com.dgmf.web.dto.PostDto;
import com.dgmf.web.dto.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(
            int pageNo, int pageSize, String sortBy, String sortDir
    );

    PostDto getPostById(Long postId);
    PostDto updatePost(PostDto postDto, Long postId);
    void deletePostById(Long postId);
}
