package com.ricardo.scalable.ecommerce.platform.userService.messaging.dummy;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.ricardo.scalable.ecommerce.platform.libs_common.events.UserRegisteredEvent;
import com.ricardo.scalable.ecommerce.platform.userService.messaging.EventPublisher;

@Profile("test")
@Component
public class DummyUserRegisteredEventPublisher implements EventPublisher<UserRegisteredEvent> {

    @Override
    public void publish(String topic, UserRegisteredEvent event) {
        // Dummy implementation: does nothing
        System.out.println("Dummy publisher: Event published to topic " + topic + " with data: " + event);
    }

}
