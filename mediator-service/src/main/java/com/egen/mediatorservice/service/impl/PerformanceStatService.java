package com.egen.mediatorservice.service.impl;

import com.egen.mediatorservice.client.MessageListener;
import com.egen.mediatorservice.client.MessagingClient;
import com.egen.mediatorservice.model.PerformanceStatisticsDTO;
import com.egen.mediatorservice.service.PerformanceStats;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PerformanceStatService implements PerformanceStats, MessageListener {

    @Value("${kafka.topic.performance-tracking-stat}")
    private String performanceStatsTopic;

    private PerformanceStatisticsDTO performanceStatisticsDTO;

    public PerformanceStatService() {
        new MessagingClient().setMessageListener(this);
    }

    @Override
    public void onMessageReceived(JsonNode message, String topic, String key, int partition, long ts) {

        if( topic.equals(performanceStatsTopic)) {
            if (message != null) {
                log.info("received message\n => {}", message);
                setMessage(new ObjectMapper().convertValue(message, PerformanceStatisticsDTO.class));
            }
        }
    }

    private void setMessage(PerformanceStatisticsDTO performanceStatisticsDTO) {
         this.performanceStatisticsDTO = performanceStatisticsDTO;
    }

    public PerformanceStatisticsDTO getMessage() {
        return performanceStatisticsDTO;
    }
}
