package com.gisma.socialconnect.repository;

import com.gisma.socialconnect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsernameIgnoreCase(String username);
    boolean existsByUsernameIgnoreCase(String username);
    boolean existsByEmailIgnoreCase(String email);
    List<User> findByUsernameContainingIgnoreCase(String usernameFragment);
}
