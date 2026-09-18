package com.gisma.socialconnect.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FollowId implements Serializable {

    @Column(name = "follower_id")
    private Integer followerId;

    @Column(name = "followee_id")
    private Integer followeeId;

    protected FollowId() {
    }

    public FollowId(Integer followerId, Integer followeeId) {
        this.followerId = followerId;
        this.followeeId = followeeId;
    }

    public Integer getFollowerId() {
        return followerId;
    }

    public Integer getFolloweeId() {
        return followeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FollowId that)) return false;
        return Objects.equals(followerId, that.followerId) && Objects.equals(followeeId, that.followeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followerId, followeeId);
    }
}
