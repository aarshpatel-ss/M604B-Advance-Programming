package com.gisma.socialconnect.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TopicFollowId implements Serializable {

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "topic_id")
    private Integer topicId;

    protected TopicFollowId() {
    }

    public TopicFollowId(Integer userId, Integer topicId) {
        this.userId = userId;
        this.topicId = topicId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getTopicId() {
        return topicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TopicFollowId that)) return false;
        return Objects.equals(userId, that.userId) && Objects.equals(topicId, that.topicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, topicId);
    }
}
