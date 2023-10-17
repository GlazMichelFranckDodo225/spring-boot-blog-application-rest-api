package com.dgmf.service;

import com.dgmf.web.dto.PostDtoRequest;
import com.dgmf.web.dto.PostDtoResponse;

public interface PostService {
    PostDtoResponse createPost(PostDtoRequest postDtoRequest);
}
