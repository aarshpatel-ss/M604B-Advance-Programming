package com.gisma.socialconnect.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TopicRequest {

    @NotBlank(message = "topicName is required")
    @Size(max = 50, message = "topicName must be at most 50 characters")
    private String topicName;

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
}
