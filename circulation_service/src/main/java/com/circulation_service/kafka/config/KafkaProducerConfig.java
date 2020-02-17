package com.circulation_service.kafka.config;

import java.util.HashMap;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.circulation_service.models.Circulation;



@Configuration
public class KafkaProducerConfig {
	
	@Value("${spring.kafka.bootstrap-servers}")
	public String serverAddress;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> kaf = new HashMap<>();
        kaf.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, serverAddress);
        kaf.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        kaf.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        kaf.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        
        return kaf;
    }

    @Bean
    public ProducerFactory<String, Circulation> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, Circulation> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}
