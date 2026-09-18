package com.gisma.socialconnect.dto;

import com.gisma.socialconnect.model.Follow;

import java.time.LocalDate;

public class FollowResponse {

    private final Integer followerId;
    private final Integer followeeId;
    private final LocalDate followedAt;

    private FollowResponse(Integer followerId, Integer followeeId, LocalDate followedAt) {
        this.followerId = followerId;
        this.followeeId = followeeId;
        this.followedAt = followedAt;
    }

    public static FollowResponse fromEntity(Follow follow) {
        return new FollowResponse(follow.getId().getFollowerId(), follow.getId().getFolloweeId(), follow.getFollowedAt());
    }

    public Integer getFollowerId() {
        return followerId;
    }

    public Integer getFolloweeId() {
        return followeeId;
    }

    public LocalDate getFollowedAt() {
        return followedAt;
    }
}
