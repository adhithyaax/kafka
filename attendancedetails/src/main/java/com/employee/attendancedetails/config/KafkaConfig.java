package com.employee.attendancedetails.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.employee.attendance.model.Employee;



@EnableKafka
@Configuration

// Class 
public class KafkaConfig {

	@Bean
	public ConsumerFactory<String, Employee> consumerFactory() {

		// Creating a map of string-object type
		Map<String, Object> config = new HashMap<>();

		// Adding the Configuration
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "0.0.0.0:9092");
		
		  config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
		 
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		JsonDeserializer<Employee> payloadJsonDeserializer = new JsonDeserializer<>();
		payloadJsonDeserializer.addTrustedPackages("*");
		// Returning message in JSON format
		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
				payloadJsonDeserializer);
	}

	// Creating a Listener
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Employee> bookListener() {
		ConcurrentKafkaListenerContainerFactory<String, Employee> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());

		return factory;
	}
}