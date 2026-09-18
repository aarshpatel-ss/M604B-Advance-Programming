package com.gisma.socialconnect.notification;

public class ReportNotification extends Notification {

    public ReportNotification(Integer recipientUserId, Long reportId, String status) {
        super(recipientUserId, "report #" + reportId + " concerning your account is now " + status);
    }

    @Override
    public String getChannel() {
        return "MODERATION";
    }

    @Override
    public String render() {
        return "[SENSITIVE] " + super.render();
    }
}
