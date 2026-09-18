package com.gisma.socialconnect.service;

import com.gisma.socialconnect.dto.UserRequest;
import com.gisma.socialconnect.dto.UserResponse;
import com.gisma.socialconnect.exception.DuplicateResourceException;
import com.gisma.socialconnect.exception.ResourceNotFoundException;
import com.gisma.socialconnect.model.User;
import com.gisma.socialconnect.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserRequest request;

    @BeforeEach
    void setUp() {
        request = new UserRequest();
        request.setUsername("newuser");
        request.setEmail("newuser@example.com");
        request.setPassword("secret123");
        request.setFullName("New User");
        request.setCountry("Testland");
    }

    @Test
    void create_savesUser_whenUsernameAndEmailAreUnique() {
        when(userRepository.existsByUsernameIgnoreCase("newuser")).thenReturn(false);
        when(userRepository.existsByEmailIgnoreCase("newuser@example.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserResponse response = userService.create(request);

        assertThat(response.getUsername()).isEqualTo("newuser");
        assertThat(response.getEmail()).isEqualTo("newuser@example.com");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void create_throwsDuplicateResourceException_whenUsernameAlreadyTaken() {
        when(userRepository.existsByUsernameIgnoreCase("newuser")).thenReturn(true);

        assertThatThrownBy(() -> userService.create(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("newuser");

        verify(userRepository, never()).save(any());
    }

    @Test
    void getById_throwsResourceNotFoundException_whenUserDoesNotExist() {
        when(userRepository.findById(999)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getById(999))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("999");
    }
}
