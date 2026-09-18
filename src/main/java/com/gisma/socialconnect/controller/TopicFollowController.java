package com.gisma.socialconnect.controller;

import com.gisma.socialconnect.dto.TopicFollowRequest;
import com.gisma.socialconnect.service.TopicFollowService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/topic-follows")
@Tag(name = "Topic Follows", description = "Topics a user has marked as followed")
public class TopicFollowController {

    private final TopicFollowService topicFollowService;

    public TopicFollowController(TopicFollowService topicFollowService) {
        this.topicFollowService = topicFollowService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void follow(@Valid @RequestBody TopicFollowRequest request) {
        topicFollowService.follow(request.getUserId(), request.getTopicId());
    }

    @DeleteMapping
    public ResponseEntity<Void> unfollow(@RequestParam Integer userId, @RequestParam Integer topicId) {
        topicFollowService.unfollow(userId, topicId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    public List<Integer> getFollowedTopicIds(@PathVariable Integer userId) {
        return topicFollowService.getFollowedTopicIds(userId);
    }
}
