package com.dgmf.service;

import com.dgmf.web.dto.PostDtoRequest;
import com.dgmf.web.dto.PostDtoResponse;

import java.util.List;

public interface PostService {
    PostDtoResponse createPost(PostDtoRequest postDtoRequest);
    List<PostDtoResponse> getAllPosts();
}
