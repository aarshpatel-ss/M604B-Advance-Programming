package com.gisma.socialconnect.notification;

import java.time.LocalDateTime;

public abstract class Notification {

    private final Integer recipientUserId;
    private final String message;
    private final LocalDateTime createdAt;

    protected Notification(Integer recipientUserId, String message) {
        this.recipientUserId = recipientUserId;
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }

    public abstract String getChannel();

    public String render() {
        return String.format("[%s] -> user #%d: %s (at %s)", getChannel(), recipientUserId, message, createdAt);
    }
}
