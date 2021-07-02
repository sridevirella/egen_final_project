package com.egen.pickupservice.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PerformanceTrackingClient {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.producer.client-id}")
    private String clientId;

    @Value("${spring.kafka.producer.key-serializer}")
    private String keySerializer;

    @Value("${spring.kafka.producer.value-serializer}")
    private String valueSerializer;

    @Value("${spring.kafka.producer.acknowledgements}")
    private String acknowledgements;

    @Value("${spring.kafka.producer.retries}")
    private String retries;

    @Value("${spring.kafka.producer.batch-size}")
    private String batchSize;

    @Value("${spring.kafka.producer.buffer-memory}")
    private String bufferMemory;

    @Value("${spring.kafka.producer.linger-ms}")
    private String lingerMs;

    @Value("${spring.kafka.producer.enable-Idempotence}")
    private String enableIdempotence;

    @Value("${spring.kafka.producer.transactionalId}")
    private String transactionalId;


    /**
     * Creates Kafka template.
     * @return kafka template instance.
     */
    @Bean
    public KafkaTemplate<String, JsonNode> pickingServiceKafkaTemplate() {

        return new KafkaTemplate<>(producerFactory());
    }

    /**
     * Produce producer instances.
     * @return singleton shared producer instance.
     */
    @Bean
    public ProducerFactory<String, JsonNode> producerFactory() {

        Map<String, Object> properties = new HashMap<>();

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        properties.put(ProducerConfig.ACKS_CONFIG, acknowledgements);
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        properties.put(ProducerConfig.LINGER_MS_CONFIG, lingerMs);
        properties.put(ProducerConfig.RETRIES_CONFIG, retries);
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, enableIdempotence);
        properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, transactionalId);

        DefaultKafkaProducerFactory<String, JsonNode> producerFactory = new DefaultKafkaProducerFactory<>(properties);

        if (transactionalId != null) {

            producerFactory.setTransactionIdPrefix(transactionalId);
            producerFactory.setProducerPerConsumerPartition(false);
        }
        return producerFactory;
    }
}
