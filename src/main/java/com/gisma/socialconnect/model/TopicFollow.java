package com.gisma.socialconnect.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Topic_Follows")
public class TopicFollow {

    @EmbeddedId
    private TopicFollowId id;

    @Column(name = "followed_at")
    private LocalDate followedAt;

    protected TopicFollow() {
    }

    public TopicFollow(Integer userId, Integer topicId) {
        this.id = new TopicFollowId(userId, topicId);
        this.followedAt = LocalDate.now();
    }

    public TopicFollowId getId() {
        return id;
    }

    public LocalDate getFollowedAt() {
        return followedAt;
    }
}
