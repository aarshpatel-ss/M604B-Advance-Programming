package com.gisma.socialconnect.service;

import com.gisma.socialconnect.exception.DuplicateResourceException;
import com.gisma.socialconnect.exception.InvalidOperationException;
import com.gisma.socialconnect.exception.ResourceNotFoundException;
import com.gisma.socialconnect.model.Follow;
import com.gisma.socialconnect.model.FollowId;
import com.gisma.socialconnect.notification.NotificationDispatcher;
import com.gisma.socialconnect.repository.FollowRepository;
import com.gisma.socialconnect.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {

    @Mock
    private FollowRepository followRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ActivityLogService activityLogService;
    @Mock
    private NotificationDispatcher notificationDispatcher;

    @InjectMocks
    private FollowService followService;

    @Test
    void follow_throwsInvalidOperationException_whenFollowingSelf() {
        assertThatThrownBy(() -> followService.follow(1, 1))
                .isInstanceOf(InvalidOperationException.class)
                .hasMessageContaining("cannot follow themselves");

        verifyNoInteractions(followRepository, activityLogService);
    }

    @Test
    void follow_throwsResourceNotFoundException_whenFolloweeDoesNotExist() {
        when(userRepository.existsById(1)).thenReturn(true);
        when(userRepository.existsById(2)).thenReturn(false);

        assertThatThrownBy(() -> followService.follow(1, 2))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void follow_throwsDuplicateResourceException_whenAlreadyFollowing() {
        when(userRepository.existsById(1)).thenReturn(true);
        when(userRepository.existsById(2)).thenReturn(true);
        when(followRepository.existsById(new FollowId(1, 2))).thenReturn(true);

        assertThatThrownBy(() -> followService.follow(1, 2))
                .isInstanceOf(DuplicateResourceException.class);
    }

    @Test
    void follow_savesFollowAndRecordsActivity_whenValid() {
        when(userRepository.existsById(1)).thenReturn(true);
        when(userRepository.existsById(2)).thenReturn(true);
        when(followRepository.existsById(new FollowId(1, 2))).thenReturn(false);
        when(followRepository.save(any(Follow.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var response = followService.follow(1, 2);

        assertThat(response.getFollowerId()).isEqualTo(1);
        assertThat(response.getFolloweeId()).isEqualTo(2);
        verify(activityLogService).record(1, "FOLLOW");
    }
}
