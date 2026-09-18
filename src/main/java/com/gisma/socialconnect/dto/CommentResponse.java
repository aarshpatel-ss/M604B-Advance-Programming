package com.gisma.socialconnect.dto;

import com.gisma.socialconnect.model.Comment;

import java.time.LocalDateTime;

public class CommentResponse {

    private final Long commentId;
    private final Long postId;
    private final Integer authorId;
    private final String content;
    private final LocalDateTime createdAt;

    private CommentResponse(Long commentId, Long postId, Integer authorId, String content, LocalDateTime createdAt) {
        this.commentId = commentId;
        this.postId = postId;
        this.authorId = authorId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static CommentResponse fromEntity(Comment comment) {
        return new CommentResponse(comment.getCommentId(), comment.getPostId(), comment.getAuthorId(),
                comment.getContent(), comment.getCreatedAt());
    }

    public Long getCommentId() {
        return commentId;
    }

    public Long getPostId() {
        return postId;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
