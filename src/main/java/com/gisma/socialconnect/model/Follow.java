package com.gisma.socialconnect.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Follows")
public class Follow {

    @EmbeddedId
    private FollowId id;

    @Column(name = "followed_at")
    private LocalDate followedAt;

    protected Follow() {
    }

    public Follow(Integer followerId, Integer followeeId) {
        this.id = new FollowId(followerId, followeeId);
        this.followedAt = LocalDate.now();
    }

    public FollowId getId() {
        return id;
    }

    public LocalDate getFollowedAt() {
        return followedAt;
    }
}
