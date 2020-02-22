package com.notification_service.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.notification_service.models.New_book;
import com.notification_service.models.ReservationMessage;
import com.notification_service.models.user_inst;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {
	
	
	@Value("${spring.kafka.bootstrap-servers}")
	String serverAddress;

	
	@Bean
	public ConsumerFactory<String, ReservationMessage> consumerFactory() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,serverAddress );
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		configs.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.notification_service.models.ReservationMessage");
		return new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), new JsonDeserializer<>(ReservationMessage.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, ReservationMessage> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, ReservationMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
	@Bean
	public ConsumerFactory<String, user_inst> newUserMessageConsumerFactory() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,serverAddress );
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		configs.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.notification_service.models.user_inst");
		return new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), new JsonDeserializer<>(user_inst.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, user_inst> newUserMesssageKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, user_inst> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(newUserMessageConsumerFactory());
		return factory;
	}
	@Bean
	public ConsumerFactory<String,New_book> newBookMessageConsumerFactory() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,serverAddress );
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		configs.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.notification_service.models.user_inst");
		return new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), new JsonDeserializer<>(New_book.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, New_book> newBookMesssageKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, New_book> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(newBookMessageConsumerFactory());
		return factory;
	}
}