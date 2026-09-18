package com.gisma.socialconnect.dto;

import jakarta.validation.constraints.NotNull;

public class FollowRequest {

    @NotNull(message = "followerId is required")
    private Integer followerId;

    @NotNull(message = "followeeId is required")
    private Integer followeeId;

    public Integer getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Integer followerId) {
        this.followerId = followerId;
    }

    public Integer getFolloweeId() {
        return followeeId;
    }

    public void setFolloweeId(Integer followeeId) {
        this.followeeId = followeeId;
    }
}
