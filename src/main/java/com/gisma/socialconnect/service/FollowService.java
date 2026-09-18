package com.gisma.socialconnect.service;

import com.gisma.socialconnect.dto.FollowResponse;
import com.gisma.socialconnect.exception.DuplicateResourceException;
import com.gisma.socialconnect.exception.InvalidOperationException;
import com.gisma.socialconnect.exception.ResourceNotFoundException;
import com.gisma.socialconnect.model.Follow;
import com.gisma.socialconnect.model.FollowId;
import com.gisma.socialconnect.notification.FollowNotification;
import com.gisma.socialconnect.notification.NotificationDispatcher;
import com.gisma.socialconnect.repository.FollowRepository;
import com.gisma.socialconnect.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final ActivityLogService activityLogService;
    private final NotificationDispatcher notificationDispatcher;

    public FollowService(FollowRepository followRepository, UserRepository userRepository,
                          ActivityLogService activityLogService, NotificationDispatcher notificationDispatcher) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
        this.activityLogService = activityLogService;
        this.notificationDispatcher = notificationDispatcher;
    }

    @Transactional
    public FollowResponse follow(Integer followerId, Integer followeeId) {
        if (followerId.equals(followeeId)) {
            throw new InvalidOperationException("A user cannot follow themselves");
        }
        if (!userRepository.existsById(followerId)) {
            throw new ResourceNotFoundException("User not found with id: " + followerId);
        }
        if (!userRepository.existsById(followeeId)) {
            throw new ResourceNotFoundException("User not found with id: " + followeeId);
        }
        FollowId id = new FollowId(followerId, followeeId);
        if (followRepository.existsById(id)) {
            throw new DuplicateResourceException("User " + followerId + " already follows user " + followeeId);
        }

        Follow saved = followRepository.save(new Follow(followerId, followeeId));
        activityLogService.record(followerId, "FOLLOW");
        notificationDispatcher.dispatch(new FollowNotification(followeeId, followerId));
        return FollowResponse.fromEntity(saved);
    }

    @Transactional
    public void unfollow(Integer followerId, Integer followeeId) {
        FollowId id = new FollowId(followerId, followeeId);
        if (!followRepository.existsById(id)) {
            throw new ResourceNotFoundException("Follow relationship not found: " + followerId + " -> " + followeeId);
        }
        followRepository.deleteById(id);
        activityLogService.record(followerId, "UNFOLLOW");
    }

    @Transactional(readOnly = true)
    public List<FollowResponse> getFollowing(Integer userId) {
        return followRepository.findById_FollowerId(userId).stream().map(FollowResponse::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public List<FollowResponse> getFollowers(Integer userId) {
        return followRepository.findById_FolloweeId(userId).stream().map(FollowResponse::fromEntity).toList();
    }
}
