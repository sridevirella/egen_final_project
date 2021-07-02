package com.egen.employeeperformanceservice.service.impl;

import com.egen.employeeperformanceservice.client.MessageListener;
import com.egen.employeeperformanceservice.client.MessagingClient;
import com.egen.employeeperformanceservice.model.entity.PerformanceStatistics;
import com.egen.employeeperformanceservice.respository.PerformanceStatsRepository;
import com.egen.employeeperformanceservice.service.PerformanceStats;
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

    private PerformanceStatsRepository performanceStatsRepository;


    public PerformanceStatService(PerformanceStatsRepository performanceStatsRepository) {

        this.performanceStatsRepository = performanceStatsRepository;
        new MessagingClient().setMessageListener(this);
    }


    @Override
    public void onMessageReceived(JsonNode message, String topic, String key, int partition, long ts) {

        if( topic.equals(performanceStatsTopic)) {
            if (message != null) {
                PerformanceStatistics statistics = performanceStatsRepository.save(new ObjectMapper().convertValue(message, PerformanceStatistics.class));
                log.info("Employee Performance statistics successfully persisted\n => {}", statistics.toString());
            }
        }
    }
}
