package com.ms.fooddelivery.order_service.service;

import com.ms.fooddelivery.order_service.model.OrderEvent;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.CompletableFuture;

@Component
public class KafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Value("${kafka.topic.order}")
    private String orderTopic;

    public void sendOrderEvent(OrderEvent event) {
        logger.info("order event : {}", event);

        logger.info("order topic : {}", orderTopic);
        CompletableFuture<SendResult<String, OrderEvent>> future = kafkaTemplate.send(orderTopic, event);

        future.whenComplete((result, throwable) -> {
            if (throwable != null) {
                logger.error("Failed to send event for Order ID {}. Error: {}",
                        event.getOrderId(), throwable.getMessage());
                // Optionally, handle the failure (e.g., send to a dead-letter topic)
            } else {
                logger.info("Successfully sent event for Order ID {}: {}",
                        event.getOrderId(), result.getRecordMetadata());
            }
        });
    }

}
