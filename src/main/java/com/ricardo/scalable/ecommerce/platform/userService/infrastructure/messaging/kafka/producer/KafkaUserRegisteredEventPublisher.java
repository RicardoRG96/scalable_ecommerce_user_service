package com.ricardo.scalable.ecommerce.platform.userService.infrastructure.messaging.kafka.producer;

import com.ricardo.scalable.ecommerce.platform.libs_common.events.UserRegisteredEvent;
import com.ricardo.scalable.ecommerce.platform.userService.messaging.EventPublisher;

public class KafkaUserRegisteredEventPublisher implements EventPublisher<UserRegisteredEvent> {

    @Override
    public void publish(String topic, UserRegisteredEvent event) {
        
    }

}
