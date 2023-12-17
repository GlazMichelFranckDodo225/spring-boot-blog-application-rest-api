package com.dgmf.service.impl;

import com.dgmf.entity.Comment;
import com.dgmf.entity.Post;
import com.dgmf.exception.BlogAPIException;
import com.dgmf.exception.ResourceNotFoundException;
import com.dgmf.repository.CommentRepository;
import com.dgmf.repository.PostRepository;
import com.dgmf.service.CommentService;
import com.dgmf.web.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        // Map CommentDto to Post Entity
        Comment comment = mapToEntity(commentDto);

        // Retrieve Post Entity by Id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );

        // Set the Post to the Comment
        comment.setPost(post);

        // Save Comment
        Comment savedComment = commentRepository.save(comment);

        // Map Post Entity to CommentDto
        CommentDto returnedCommentDto = mapToDto(savedComment);


        return returnedCommentDto;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        // Retrieved Comments By Post Id
        List<Comment> comments = commentRepository.findByPostId(postId);
        // Convert a List of Comments into a List of CommentDtos
        List<CommentDto> commentDtos = comments.stream()
                .map(comment -> mapToDto(comment))
                .collect(Collectors.toList());

        return commentDtos;
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        // Retrieve Post Entity by Id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );

        // Retrieved Comment By Id
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId)
        );

        // If Comment doesn't belong to Post
        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(
                    HttpStatus.BAD_REQUEST,
                    "Comment doesn't belong to Post"
            );
        }

        CommentDto commentDto = mapToDto(comment);

        return commentDto;
    }

    private CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = CommentDto.builder()
                .id(comment.getId())
                .name(comment.getName())
                .email(comment.getEmail())
                .body(comment.getBody())
                .build();

        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = Comment.builder()
                .id(commentDto.getId())
                .name(commentDto.getName())
                .email(commentDto.getEmail())
                .body(commentDto.getBody())
                .build();

        return comment;
    }
}
