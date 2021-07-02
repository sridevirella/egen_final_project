package com.egen.pickupservice.client.impl;

import com.egen.pickupservice.client.PerformanceTrackingClient;
import com.egen.pickupservice.model.dto.EmployeePerfStatsDTO;
import com.egen.pickupservice.model.dto.client.EmployeePerformanceDTO;
import com.egen.pickupservice.model.entity.EmployeePerformance;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
public class PerformanceTrackingClientImpl implements PerformanceTrackingClient {

    @Value("${kafka.topic.performance-tracking-stat}")
    private String topic;


    private final KafkaTemplate<String, JsonNode> pickingServiceKafkaTemplate;

    public PerformanceTrackingClientImpl(KafkaTemplate<String, JsonNode> pickingServiceKafkaTemplate) {
        this.pickingServiceKafkaTemplate = pickingServiceKafkaTemplate;
    }

    @Override
    public void employeePerformanceDetails(EmployeePerformanceDTO employeePerformanceDTO) {

        sendEmployeePerformanceStats(topic,
                                     employeePerformanceDTO.getPickEmpPerfId(),
                                     new ObjectMapper().convertValue(employeePerformanceDTO, JsonNode.class));

    }

    /**
     * produce each message to kafka with a call back.
     */
    private void sendEmployeePerformanceStats(String topic, String key, JsonNode message) {

        pickingServiceKafkaTemplate.executeInTransaction(transaction -> {

            ListenableFuture<SendResult<String, JsonNode>> future = transaction.send(topic, key, message);

            future.addCallback(new ListenableFutureCallback<>() {

                @Override
                public void onSuccess(SendResult<String, JsonNode> result) {
                    RecordMetadata sentMessage = result.getRecordMetadata();
                    log.info("=> on success produced message {} {} {} {}", result.getProducerRecord(), sentMessage.offset(), sentMessage.topic(), sentMessage.partition());
                }

                @Override
                public void onFailure(Throwable ex) {
                    log.info("Failed to produce message=[ {} ] due to : {}", message, ex.getMessage());
                }
            });
            return true;
        });
    }
}
