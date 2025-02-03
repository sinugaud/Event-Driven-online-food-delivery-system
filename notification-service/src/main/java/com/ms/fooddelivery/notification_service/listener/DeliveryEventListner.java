package com.ms.fooddelivery.notification_service.listener;

import com.ms.fooddelivery.notification_service.model.OrderEvent;
import com.ms.fooddelivery.notification_service.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;

public class DeliveryEventListner {
    private NotificationService notificationService;

    public DeliveryEventListner(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // Listen to the order events topic.
    @KafkaListener(topics = "delivery-events", groupId = "notification-service-group")
    public void listenDeliveryEvents(OrderEvent event) {
        notificationService.sendNotification(event);
    }
}

