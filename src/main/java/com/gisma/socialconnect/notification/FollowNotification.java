package com.gisma.socialconnect.notification;

public class FollowNotification extends Notification {

    public FollowNotification(Integer recipientUserId, Integer followerId) {
        super(recipientUserId, "user #" + followerId + " started following you");
    }

    @Override
    public String getChannel() {
        return "FOLLOW";
    }
}
