package com.gisma.socialconnect.dto;

import com.gisma.socialconnect.model.Post;

import java.time.LocalDateTime;

public class PostResponse {

    private final Long postId;
    private final Integer authorId;
    private final Integer topicId;
    private final String content;
    private final LocalDateTime createdAt;

    private PostResponse(Long postId, Integer authorId, Integer topicId, String content, LocalDateTime createdAt) {
        this.postId = postId;
        this.authorId = authorId;
        this.topicId = topicId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static PostResponse fromEntity(Post post) {
        return new PostResponse(post.getPostId(), post.getAuthorId(), post.getTopicId(), post.getContent(), post.getCreatedAt());
    }

    public Long getPostId() {
        return postId;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
