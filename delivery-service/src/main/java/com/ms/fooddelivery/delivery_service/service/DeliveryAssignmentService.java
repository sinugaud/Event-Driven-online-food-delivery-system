// File: DeliveryAssignmentService.java
package com.ms.fooddelivery.delivery_service.service;

import com.ms.fooddelivery.delivery_service.model.DeliveryEvent;
import com.ms.fooddelivery.delivery_service.model.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DeliveryAssignmentService {

    private static final Logger logger = LoggerFactory.getLogger(DeliveryAssignmentService.class);

    private final KafkaTemplate<String, DeliveryEvent> kafkaTemplate;

    @Value("${kafka.topic.delivery}")
    private String deliveryTopic;

    public DeliveryAssignmentService(KafkaTemplate<String, DeliveryEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Simulate assigning a delivery person when an order is created.
     */
    public void assignDelivery(OrderEvent orderEvent) {
        logger.info("Assigning delivery for Order ID {}", orderEvent.getOrderId());
        // Create a delivery event (for example, mark it as "ASSIGNED")
        DeliveryEvent deliveryEvent = new DeliveryEvent(
            orderEvent.getOrderId(), 
            "ASSIGNED", 
            new Date()
        );
        // Publish the delivery event to the designated Kafka topic
        kafkaTemplate.send(deliveryTopic, deliveryEvent);
        logger.info("Published DeliveryEvent for Order ID {} to topic {}", orderEvent.getOrderId(), deliveryTopic);
    }
}
