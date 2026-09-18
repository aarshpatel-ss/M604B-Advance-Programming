package com.gisma.socialconnect.repository;

import com.gisma.socialconnect.model.TopicFollow;
import com.gisma.socialconnect.model.TopicFollowId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicFollowRepository extends JpaRepository<TopicFollow, TopicFollowId> {
    List<TopicFollow> findById_UserId(Integer userId);
}
