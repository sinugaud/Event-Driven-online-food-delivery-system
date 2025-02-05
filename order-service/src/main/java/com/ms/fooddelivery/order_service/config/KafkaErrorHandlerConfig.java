package com.ms.fooddelivery.order_service.config;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;

@Configuration
public class KafkaErrorHandlerConfig {

    @Value("${kafka.topic.delivery}")
    private String deliveryTopic; // This might be "delivery-events"

    @Value("${kafka.topic.delivery.dlq:delivery-events.DLT}")
    private String dlqTopic;

    /**
     * Configure the error handler to use a DeadLetterPublishingRecoverer.
     * The error handler will try to process the message a few times before publishing
     * it to the DLQ.
     */
    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<Object, Object> kafkaTemplate) {
        // You can use an ExponentialBackOff or FixedBackOff policy
        ExponentialBackOffWithMaxRetries backOff = new ExponentialBackOffWithMaxRetries(3);
        backOff.setInitialInterval(1000L);
        backOff.setMultiplier(2.0);
        backOff.setMaxInterval(10000L);

        // Create a recoverer that publishes to the DLQ topic
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,
            // Optionally, customize the destination for each failed message:
            (record, exception) -> new TopicPartition(dlqTopic, record.partition())
        );

        // The DefaultErrorHandler uses the recoverer to send messages to the DLQ after retries.
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(recoverer, backOff);

        // You can add additional configuration here if necessary
        return errorHandler;
    }
}
