package com.gisma.socialconnect.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "author_id", nullable = false)
    private Integer authorId;

    @Column(name = "content", nullable = false, length = 300)
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    protected Comment() {
    }

    public Comment(Long postId, Integer authorId, String content) {
        this.postId = postId;
        this.authorId = authorId;
        this.content = content;
        this.createdAt = LocalDateTime.now();
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
