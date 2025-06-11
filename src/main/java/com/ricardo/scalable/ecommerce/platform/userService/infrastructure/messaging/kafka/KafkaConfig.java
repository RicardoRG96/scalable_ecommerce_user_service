package com.ricardo.scalable.ecommerce.platform.userService.infrastructure.messaging.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.ricardo.scalable.ecommerce.platform.libs_common.events.UserBirthdayEvent;
import com.ricardo.scalable.ecommerce.platform.libs_common.events.UserRegisteredEvent;

@Configuration
public class KafkaConfig {

    @Bean
    ProducerFactory<String, UserRegisteredEvent> userRegisteredProducerFactory() {
        Map<String, Object> props = baseProducerConfig();
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    KafkaTemplate<String, UserRegisteredEvent> userRegisteredKafkaTemplate() {
        return new KafkaTemplate<>(userRegisteredProducerFactory());
    }

    @Bean
    ProducerFactory<String, UserBirthdayEvent> userBirthdayProducerFactory() {
        Map<String, Object> props = baseProducerConfig();
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    KafkaTemplate<String, UserBirthdayEvent> userBirthdayKafkaTemplate() {
        return new KafkaTemplate<>(userBirthdayProducerFactory());
    }

    private Map<String, Object> baseProducerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    } 

}
