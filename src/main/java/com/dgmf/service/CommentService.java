package com.dgmf.service;

import com.dgmf.entity.Post;
import com.dgmf.web.dto.CommentDto;

public interface CommentService {
    CommentDto createComment(Long postId, CommentDto commentDto);
}
