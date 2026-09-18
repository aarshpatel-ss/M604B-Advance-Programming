package com.gisma.socialconnect.controller;

import com.gisma.socialconnect.dto.FollowRequest;
import com.gisma.socialconnect.dto.FollowResponse;
import com.gisma.socialconnect.service.FollowService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/follows")
@Tag(name = "Follows", description = "Create/read/delete follow relationships between users")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FollowResponse follow(@Valid @RequestBody FollowRequest request) {
        return followService.follow(request.getFollowerId(), request.getFolloweeId());
    }

    @DeleteMapping
    public ResponseEntity<Void> unfollow(@RequestParam Integer followerId, @RequestParam Integer followeeId) {
        followService.unfollow(followerId, followeeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/following")
    public List<FollowResponse> getFollowing(@PathVariable Integer userId) {
        return followService.getFollowing(userId);
    }

    @GetMapping("/{userId}/followers")
    public List<FollowResponse> getFollowers(@PathVariable Integer userId) {
        return followService.getFollowers(userId);
    }
}
