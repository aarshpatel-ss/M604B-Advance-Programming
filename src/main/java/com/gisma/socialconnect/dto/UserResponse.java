package com.gisma.socialconnect.dto;

import com.gisma.socialconnect.model.User;

import java.time.LocalDate;

public class UserResponse {

    private final Integer userId;
    private final String username;
    private final String email;
    private final String fullName;
    private final String country;
    private final LocalDate createdAt;

    private UserResponse(Integer userId, String username, String email, String fullName, String country, LocalDate createdAt) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.country = country;
        this.createdAt = createdAt;
    }

    public static UserResponse fromEntity(User user) {
        return new UserResponse(user.getUserId(), user.getUsername(), user.getEmail(),
                user.getFullName(), user.getCountry(), user.getCreatedAt());
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getCountry() {
        return country;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }
}
