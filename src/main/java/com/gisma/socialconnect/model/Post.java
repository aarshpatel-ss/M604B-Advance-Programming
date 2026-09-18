package com.gisma.socialconnect.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "author_id", nullable = false)
    private Integer authorId;

    @Column(name = "topic_id", nullable = false)
    private Integer topicId;

    @Column(name = "content", nullable = false, length = 200)
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    protected Post() {
    }

    public Post(Integer authorId, Integer topicId, String content) {
        this.authorId = authorId;
        this.topicId = topicId;
        this.content = content;
        this.createdAt = LocalDateTime.now();
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

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
