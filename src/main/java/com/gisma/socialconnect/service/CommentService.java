package com.gisma.socialconnect.service;

import com.gisma.socialconnect.dto.CommentRequest;
import com.gisma.socialconnect.dto.CommentResponse;
import com.gisma.socialconnect.exception.ResourceNotFoundException;
import com.gisma.socialconnect.model.Comment;
import com.gisma.socialconnect.repository.CommentRepository;
import com.gisma.socialconnect.repository.PostRepository;
import com.gisma.socialconnect.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ActivityLogService activityLogService;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository,
                           UserRepository userRepository, ActivityLogService activityLogService) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.activityLogService = activityLogService;
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getByPost(Long postId) {
        return commentRepository.findByPostIdOrderByCreatedAtAsc(postId).stream().map(CommentResponse::fromEntity).toList();
    }

    @Transactional
    public CommentResponse create(Long postId, CommentRequest request) {
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Post not found with id: " + postId);
        }
        if (!userRepository.existsById(request.getAuthorId())) {
            throw new ResourceNotFoundException("User not found with id: " + request.getAuthorId());
        }
        Comment saved = commentRepository.save(new Comment(postId, request.getAuthorId(), request.getContent()));
        activityLogService.record(request.getAuthorId(), "COMMENT_ADDED");
        return CommentResponse.fromEntity(saved);
    }

    @Transactional
    public void delete(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new ResourceNotFoundException("Comment not found with id: " + commentId);
        }
        commentRepository.deleteById(commentId);
    }
}
