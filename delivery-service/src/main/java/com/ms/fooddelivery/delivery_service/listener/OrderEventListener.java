package com.ms.fooddelivery.delivery_service.listener;


import com.ms.fooddelivery.delivery_service.model.OrderEvent;
import com.ms.fooddelivery.delivery_service.service.DeliveryAssignmentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class OrderEventListener {

    @Autowired
    private DeliveryAssignmentService deliveryAssignmentService;

    //    @KafkaListener(topics = "${kafka.topic.order}", groupId = "delivery-service-group")
//    public void listenOrderEvents(OrderEvent event) {
//        log.info("order event listen : {}", event);
//        // For example, only trigger delivery assignment on ORDER_CREATED events.
//        if ("ORDER_CREATED".equalsIgnoreCase(event.getEventType())) {
//            deliveryAssignmentService.assignDelivery(event);
//            log.info("Delivery assgined");
//
//        }
//    }
    @RetryableTopic(attempts = "4", backoff = @Backoff(delay = 1000, multiplier = 2), dltTopicSuffix = ".DLT")
    @KafkaListener(topics = "${kafka.topic.order}", groupId = "order-service-group")
//            containerFactory = "kafkaListenerContainerFactory")
    public void listenOrderEvents(OrderEvent event) {
        log.info("Received Order Event: {}", event);

        log.info("event type : {}", event.getEventType());
        // Simulate a processing failure for a specific condition
        if ("ORDER_CREATED".equals(event.getEventType())) {
            throw new RuntimeException("Simulated processing failure to test DLQ");
        }

        // Otherwise, process the event normally...
        log.info("Processed Order Event: {}", event);
    }

    @DltHandler
    public void dtlHandler(OrderEvent failedEvent,
//                           @Header(name = KafkaHeaders.DLT_EXCEPTION_MESSAGE, required = true) String dltExceptionMessage,
                           @Header(name = KafkaHeaders.RECEIVED_TOPIC, required = false) String topic) {
        log.error("DLT handler: Failed event: {} from topic: {} with error: ",
                failedEvent, topic);
    }

}