package com.gisma.socialconnect.service;

import com.gisma.socialconnect.dto.UserResponse;
import com.gisma.socialconnect.exception.AuthenticationFailedException;
import com.gisma.socialconnect.model.User;
import com.gisma.socialconnect.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public UserResponse login(String username, String password) {
        User user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new AuthenticationFailedException("Invalid username or password"));
        if (!user.getPasswordHash().equals(password)) {
            throw new AuthenticationFailedException("Invalid username or password");
        }
        return UserResponse.fromEntity(user);
    }
}
