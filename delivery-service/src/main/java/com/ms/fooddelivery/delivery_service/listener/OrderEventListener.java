// File: OrderEventListener.java
package com.ms.fooddelivery.delivery_service.listener;

import com.ms.fooddelivery.delivery_service.model.OrderEvent;
import com.ms.fooddelivery.delivery_service.service.DeliveryAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    @Autowired
    private DeliveryAssignmentService deliveryAssignmentService;

    // Listen to the order events topic.
    @KafkaListener(topics = "${kafka.topic.order}", groupId = "delivery-service-group")
    public void listenOrderEvents(OrderEvent event) {
        // For example, only trigger delivery assignment on ORDER_CREATED events.
        if ("ORDER_CREATED".equalsIgnoreCase(event.getEventType())) {
            deliveryAssignmentService.assignDelivery(event);
        }
    }
}
