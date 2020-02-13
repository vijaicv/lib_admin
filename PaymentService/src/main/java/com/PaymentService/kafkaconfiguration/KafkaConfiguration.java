package com.PaymentService.kafkaconfiguration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.PaymentService.model.ReturnMessage;

@Configuration
@EnableKafka
public class KafkaConfiguration {

	@Bean
	public ConsumerFactory<String, ReturnMessage> consumerFactory(){
		Map<String, Object> configs =  new HashMap<>();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.43.79:9092");
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		configs.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.PaymentService.model.ReturnMessage");
		return new DefaultKafkaConsumerFactory<>(configs);
		
	}
	 
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, ReturnMessage> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, ReturnMessage>  factory = new ConcurrentKafkaListenerContainerFactory<String, ReturnMessage>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

}
