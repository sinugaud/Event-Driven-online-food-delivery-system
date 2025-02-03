// File: NotificationService.java
package com.ms.fooddelivery.notification_service.service;

import com.ms.fooddelivery.notification_service.model.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    /**
     * Simulate sending a notification based on the order event.
     */
    public void sendNotification(OrderEvent event) {
        // Customize this method to integrate with an actual notification (email/SMS/push) provider.
        String notificationMessage = String.format(
            "Notification: Order %d has status '%s' at %s",
            event.getOrderId(), event.getEventType(), event.getTimestamp()
        );
        logger.info(notificationMessage);
    }
}
