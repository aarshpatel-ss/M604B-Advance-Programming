package com.gisma.socialconnect.repository;

import com.gisma.socialconnect.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();
    List<Post> findByTopicIdOrderByCreatedAtDesc(Integer topicId);
    List<Post> findByAuthorIdOrderByCreatedAtDesc(Integer authorId);
}
