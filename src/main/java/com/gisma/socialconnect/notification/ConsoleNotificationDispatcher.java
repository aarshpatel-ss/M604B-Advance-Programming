package com.gisma.socialconnect.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ConsoleNotificationDispatcher implements NotificationDispatcher {

    private static final Logger log = LoggerFactory.getLogger(ConsoleNotificationDispatcher.class);

    @Override
    public void dispatch(Notification notification) {
        log.info(notification.render());
    }
}
