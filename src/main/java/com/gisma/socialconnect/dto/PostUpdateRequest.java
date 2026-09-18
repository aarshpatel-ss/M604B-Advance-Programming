package com.gisma.socialconnect.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PostUpdateRequest {

    @NotNull(message = "topicId is required")
    private Integer topicId;

    @NotBlank(message = "content is required")
    @Size(max = 200, message = "content must be at most 200 characters")
    private String content;

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
}
