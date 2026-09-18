package com.gisma.socialconnect.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CommentRequest {

    @NotNull(message = "authorId is required")
    private Integer authorId;

    @NotBlank(message = "content is required")
    @Size(max = 300, message = "content must be at most 300 characters")
    private String content;

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
