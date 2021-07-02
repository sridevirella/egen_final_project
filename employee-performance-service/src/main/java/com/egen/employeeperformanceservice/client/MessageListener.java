package com.egen.employeeperformanceservice.client;

import com.fasterxml.jackson.databind.JsonNode;

public interface MessageListener {

    void onMessageReceived(JsonNode message, String topic, String key, int partition, long ts);
}
