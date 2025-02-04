package com.ms.fooddelivery.notification_service.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.fooddelivery.notification_service.model.DeliveryEvent;
import com.ms.fooddelivery.notification_service.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DeliveryEventListner {
    private NotificationService notificationService;

    public DeliveryEventListner(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    @KafkaListener(
            topics = "delivery-events",
            groupId = "notification-service-group",
            containerFactory = "kafkaListenerContainerFactoryDeliveryEvent"
    )
    public void listenDeliveryEvents(DeliveryEvent event) {
        try {
            log.info("Received delivery event: {}", event);
            notificationService.sendNotification(event);
        } catch (Exception e) {
            log.error("Error processing delivery event: {}", e.getMessage());
        }
    }

}

