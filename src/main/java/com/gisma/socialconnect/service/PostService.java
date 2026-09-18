package com.gisma.socialconnect.service;

import com.gisma.socialconnect.dto.PostRequest;
import com.gisma.socialconnect.dto.PostResponse;
import com.gisma.socialconnect.dto.PostUpdateRequest;
import com.gisma.socialconnect.exception.ResourceNotFoundException;
import com.gisma.socialconnect.model.Post;
import com.gisma.socialconnect.repository.PostRepository;
import com.gisma.socialconnect.repository.TopicRepository;
import com.gisma.socialconnect.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final ActivityLogService activityLogService;

    public PostService(PostRepository postRepository, UserRepository userRepository, TopicRepository topicRepository,
                        ActivityLogService activityLogService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.activityLogService = activityLogService;
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAll() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream().map(PostResponse::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getByTopic(Integer topicId) {
        return postRepository.findByTopicIdOrderByCreatedAtDesc(topicId).stream().map(PostResponse::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getByAuthor(Integer authorId) {
        return postRepository.findByAuthorIdOrderByCreatedAtDesc(authorId).stream().map(PostResponse::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public PostResponse getById(Long id) {
        return PostResponse.fromEntity(findEntityOrThrow(id));
    }

    @Transactional
    public PostResponse create(PostRequest request) {
        if (!userRepository.existsById(request.getAuthorId())) {
            throw new ResourceNotFoundException("User not found with id: " + request.getAuthorId());
        }
        if (!topicRepository.existsById(request.getTopicId())) {
            throw new ResourceNotFoundException("Topic not found with id: " + request.getTopicId());
        }
        Post saved = postRepository.save(new Post(request.getAuthorId(), request.getTopicId(), request.getContent()));
        activityLogService.record(request.getAuthorId(), "POST_CREATED");
        return PostResponse.fromEntity(saved);
    }

    @Transactional
    public PostResponse update(Long id, PostUpdateRequest request) {
        Post post = findEntityOrThrow(id);
        if (!topicRepository.existsById(request.getTopicId())) {
            throw new ResourceNotFoundException("Topic not found with id: " + request.getTopicId());
        }
        post.setTopicId(request.getTopicId());
        post.setContent(request.getContent());
        return PostResponse.fromEntity(postRepository.save(post));
    }

    @Transactional
    public void delete(Long id) {
        if (!postRepository.existsById(id)) {
            throw new ResourceNotFoundException("Post not found with id: " + id);
        }
        postRepository.deleteById(id);
    }

    private Post findEntityOrThrow(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
    }
}
