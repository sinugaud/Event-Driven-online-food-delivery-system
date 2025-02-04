package com.ms.fooddelivery.delivery_service.listener;

import com.ms.fooddelivery.delivery_service.model.OrderEvent;
import com.ms.fooddelivery.delivery_service.service.DeliveryAssignmentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class OrderEventListener {

    @Autowired
    private DeliveryAssignmentService deliveryAssignmentService;

    @KafkaListener(topics = "${kafka.topic.order}", groupId = "delivery-service-group")
    public void listenOrderEvents(OrderEvent event) {
        log.info("order event listen : {}", event);
        // For example, only trigger delivery assignment on ORDER_CREATED events.
        if ("ORDER_CREATED".equalsIgnoreCase(event.getEventType())) {
            deliveryAssignmentService.assignDelivery(event);
            log.info("Delivery assgined");

        }
    }
}
