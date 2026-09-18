package com.gisma.socialconnect.service;

import com.gisma.socialconnect.dto.UserRequest;
import com.gisma.socialconnect.dto.UserResponse;
import com.gisma.socialconnect.exception.DuplicateResourceException;
import com.gisma.socialconnect.exception.ResourceNotFoundException;
import com.gisma.socialconnect.model.User;
import com.gisma.socialconnect.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAll() {
        return userRepository.findAll().stream().map(UserResponse::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public List<UserResponse> search(String usernameFragment) {
        return userRepository.findByUsernameContainingIgnoreCase(usernameFragment).stream()
                .map(UserResponse::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public UserResponse getById(Integer id) {
        return UserResponse.fromEntity(findEntityOrThrow(id));
    }

    @Transactional
    public UserResponse create(UserRequest request) {
        if (userRepository.existsByUsernameIgnoreCase(request.getUsername())) {
            throw new DuplicateResourceException("Username '" + request.getUsername() + "' is already taken");
        }
        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new DuplicateResourceException("Email '" + request.getEmail() + "' is already registered");
        }
        User user = new User(request.getUsername(), request.getEmail(),
                request.getPassword(), request.getFullName(), request.getCountry());
        return UserResponse.fromEntity(userRepository.save(user));
    }

    @Transactional
    public UserResponse update(Integer id, UserRequest request) {
        User user = findEntityOrThrow(id);

        userRepository.findByUsernameIgnoreCase(request.getUsername())
                .filter(existing -> !existing.getUserId().equals(id))
                .ifPresent(existing -> {
                    throw new DuplicateResourceException("Username '" + request.getUsername() + "' is already taken");
                });

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPasswordHash(request.getPassword());
        user.setFullName(request.getFullName());
        user.setCountry(request.getCountry());
        return UserResponse.fromEntity(userRepository.save(user));
    }

    @Transactional
    public void delete(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    private User findEntityOrThrow(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
}
