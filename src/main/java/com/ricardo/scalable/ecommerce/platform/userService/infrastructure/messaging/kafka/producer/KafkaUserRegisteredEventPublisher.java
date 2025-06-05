package com.ricardo.scalable.ecommerce.platform.userService.infrastructure.messaging.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.ricardo.scalable.ecommerce.platform.libs_common.events.UserRegisteredEvent;
import com.ricardo.scalable.ecommerce.platform.userService.messaging.EventPublisher;

@Component
public class KafkaUserRegisteredEventPublisher implements EventPublisher<UserRegisteredEvent> {

    @Autowired
    private final KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate;

    public KafkaUserRegisteredEventPublisher(KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(String topic, UserRegisteredEvent event) {
        kafkaTemplate.send(topic, event);
    }

}
