package com.gisma.socialconnect.service;

import com.gisma.socialconnect.exception.DuplicateResourceException;
import com.gisma.socialconnect.exception.ResourceNotFoundException;
import com.gisma.socialconnect.model.TopicFollow;
import com.gisma.socialconnect.model.TopicFollowId;
import com.gisma.socialconnect.repository.TopicFollowRepository;
import com.gisma.socialconnect.repository.TopicRepository;
import com.gisma.socialconnect.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TopicFollowService {

    private final TopicFollowRepository topicFollowRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    public TopicFollowService(TopicFollowRepository topicFollowRepository, UserRepository userRepository,
                               TopicRepository topicRepository) {
        this.topicFollowRepository = topicFollowRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }

    @Transactional
    public void follow(Integer userId, Integer topicId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        if (!topicRepository.existsById(topicId)) {
            throw new ResourceNotFoundException("Topic not found with id: " + topicId);
        }
        TopicFollowId id = new TopicFollowId(userId, topicId);
        if (topicFollowRepository.existsById(id)) {
            throw new DuplicateResourceException("User " + userId + " already follows topic " + topicId);
        }
        topicFollowRepository.save(new TopicFollow(userId, topicId));
    }

    @Transactional
    public void unfollow(Integer userId, Integer topicId) {
        TopicFollowId id = new TopicFollowId(userId, topicId);
        if (!topicFollowRepository.existsById(id)) {
            throw new ResourceNotFoundException("This user does not follow topic " + topicId);
        }
        topicFollowRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Integer> getFollowedTopicIds(Integer userId) {
        return topicFollowRepository.findById_UserId(userId).stream()
                .map(tf -> tf.getId().getTopicId())
                .toList();
    }
}
