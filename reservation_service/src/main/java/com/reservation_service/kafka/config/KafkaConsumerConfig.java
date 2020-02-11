package com.reservation_service.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.reservation_service.models.BookReturnMessage;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Bean
  public Map<String, Object> consumerConfigs() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "json");

    return props;
  }

  @Bean
  public ConsumerFactory<String, BookReturnMessage> consumerFactory() {
    return new DefaultKafkaConsumerFactory<String, BookReturnMessage>(consumerConfigs());
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String,BookReturnMessage > kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, BookReturnMessage> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());

    return factory;
  }

 
}
