package com.ricardo.scalable.ecommerce.platform.userService.infrastructure.messaging.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.ricardo.scalable.ecommerce.platform.libs_common.events.UserBirthdayEvent;
import com.ricardo.scalable.ecommerce.platform.userService.messaging.EventPublisher;

@Component
public class KafkaUserBirthdayEventPublisher implements EventPublisher<UserBirthdayEvent> {

    @Autowired
    private final KafkaTemplate<String, UserBirthdayEvent> kafkaTemplate;

    public KafkaUserBirthdayEventPublisher(KafkaTemplate<String, UserBirthdayEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(String topic, UserBirthdayEvent event) {
        kafkaTemplate.send(topic, event);
    }

}
