// File: OrderEventListener.java
package com.ms.fooddelivery.notification_service.listener;

import com.ms.fooddelivery.notification_service.model.DeliveryEvent;
import com.ms.fooddelivery.notification_service.model.OrderEvent;
import com.ms.fooddelivery.notification_service.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderEventListener {

    private NotificationService notificationService;

    public OrderEventListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "${kafka.topic.order}", groupId = "notification-service-group")
    public void listenOrderEvents(OrderEvent event) {
        log.info("order event notification : {}", event);
        notificationService.sendNotification(event);
    }
}
