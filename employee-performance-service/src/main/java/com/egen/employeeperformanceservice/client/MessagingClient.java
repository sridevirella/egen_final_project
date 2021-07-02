package com.egen.employeeperformanceservice.client;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessagingClient {

    MessageListener messageListener;

    public MessagingClient() {}

    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    /**
     * A listener that receives messages on a specified list of topics.
     */
    @KafkaListener(containerFactory = "kafkaListenerContainerFactory",
                   topics = {"${kafka.topic.performance-tracking-stat}"},
                   groupId = "${kafka.consumer.groupId}")
    public void orderServiceDataListener(@Payload JsonNode jsonNode,
                                         @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                                         @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                         @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts) {

        log.info("Consumed Message: {} {} {} {} {}", jsonNode.toString(), topic, key, partition, ts);
        if(this.messageListener != null)
            messageListener.onMessageReceived(jsonNode, topic, key, partition, ts);
    }
}
