package com.gisma.socialconnect.repository;

import com.gisma.socialconnect.model.Follow;
import com.gisma.socialconnect.model.FollowId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, FollowId> {
    List<Follow> findById_FollowerId(Integer followerId);
    List<Follow> findById_FolloweeId(Integer followeeId);
    long countById_FolloweeId(Integer followeeId);
}
