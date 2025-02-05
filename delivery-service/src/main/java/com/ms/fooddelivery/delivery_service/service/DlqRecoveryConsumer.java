package com.ms.fooddelivery.delivery_service.service;

import com.ms.fooddelivery.delivery_service.model.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DlqRecoveryConsumer {

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    // Listen to the DLQ topic
    @KafkaListener(topics = "${kafka.topic.order.dlq:order-events.DLT}", groupId = "order-dlt-recovery-group")
    public void listenDlq(OrderEvent failedEvent,
                          @Header(name = KafkaHeaders.DLT_EXCEPTION_MESSAGE, required = false) String dltExceptionMessage,
                          @Header(name = KafkaHeaders.RECEIVED_TOPIC, required = false) String originalTopic) {
        log.info("Recovered message from DLQ: {} with error: {}", failedEvent, dltExceptionMessage);
        
        // Here you can add logic to decide whether to reprocess automatically,
        // or perhaps check if the underlying issue has been resolved.
        // For example, if you determine the issue is fixed, republish:
        kafkaTemplate.send("order-events", failedEvent);
        log.info("Republished failed message to original topic: order-events");
    }
}
