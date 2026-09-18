package com.gisma.socialconnect.dto;

import jakarta.validation.constraints.NotNull;

public class TopicFollowRequest {

    @NotNull(message = "userId is required")
    private Integer userId;

    @NotNull(message = "topicId is required")
    private Integer topicId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }
}
