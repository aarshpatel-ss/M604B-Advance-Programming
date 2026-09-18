package com.gisma.socialconnect.service;

import com.gisma.socialconnect.dto.TopicRequest;
import com.gisma.socialconnect.dto.TopicResponse;
import com.gisma.socialconnect.exception.DuplicateResourceException;
import com.gisma.socialconnect.exception.ResourceNotFoundException;
import com.gisma.socialconnect.model.Topic;
import com.gisma.socialconnect.repository.TopicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Transactional(readOnly = true)
    public List<TopicResponse> getAll() {
        return topicRepository.findAll().stream().map(TopicResponse::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public List<TopicResponse> search(String nameFragment) {
        return topicRepository.findByTopicNameContainingIgnoreCase(nameFragment).stream()
                .map(TopicResponse::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public TopicResponse getById(Integer id) {
        return TopicResponse.fromEntity(findEntityOrThrow(id));
    }

    @Transactional
    public TopicResponse create(TopicRequest request) {
        if (topicRepository.existsByTopicNameIgnoreCase(request.getTopicName())) {
            throw new DuplicateResourceException("Topic '" + request.getTopicName() + "' already exists");
        }
        return TopicResponse.fromEntity(topicRepository.save(new Topic(request.getTopicName())));
    }

    @Transactional
    public TopicResponse update(Integer id, TopicRequest request) {
        Topic topic = findEntityOrThrow(id);
        topicRepository.findByTopicNameIgnoreCase(request.getTopicName())
                .filter(existing -> !existing.getTopicId().equals(id))
                .ifPresent(existing -> {
                    throw new DuplicateResourceException("Topic '" + request.getTopicName() + "' already exists");
                });
        topic.setTopicName(request.getTopicName());
        return TopicResponse.fromEntity(topicRepository.save(topic));
    }

    @Transactional
    public void delete(Integer id) {
        if (!topicRepository.existsById(id)) {
            throw new ResourceNotFoundException("Topic not found with id: " + id);
        }
        topicRepository.deleteById(id);
    }

    private Topic findEntityOrThrow(Integer id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found with id: " + id));
    }
}
