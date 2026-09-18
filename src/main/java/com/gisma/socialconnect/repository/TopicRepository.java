package com.gisma.socialconnect.repository;

import com.gisma.socialconnect.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Integer> {
    Optional<Topic> findByTopicNameIgnoreCase(String topicName);
    boolean existsByTopicNameIgnoreCase(String topicName);
    List<Topic> findByTopicNameContainingIgnoreCase(String topicNameFragment);
}
