package com.ricardo.scalable.ecommerce.platform.userService.messaging;

public interface EventPublisher<T> {

    void publish(String topic, T event);

}
