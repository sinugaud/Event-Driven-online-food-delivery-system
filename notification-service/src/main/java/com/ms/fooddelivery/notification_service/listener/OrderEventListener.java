// File: OrderEventListener.java
package com.ms.fooddelivery.notification_service.listener;

import com.ms.fooddelivery.notification_service.model.OrderEvent;
import com.ms.fooddelivery.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    @Autowired
    private NotificationService notificationService;

    // Listen to the order events topic.
    @KafkaListener(topics = "${kafka.topic.order}", groupId = "notification-service-group")
    public void listenOrderEvents(OrderEvent event) {
        notificationService.sendNotification(event);
    }
}
