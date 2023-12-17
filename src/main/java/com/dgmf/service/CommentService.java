package com.dgmf.service;

import com.dgmf.entity.Comment;
import com.dgmf.entity.Post;
import com.dgmf.web.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(Long postId, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(Long postId);
    CommentDto getCommentById(Long postId, Long commentId);
}
