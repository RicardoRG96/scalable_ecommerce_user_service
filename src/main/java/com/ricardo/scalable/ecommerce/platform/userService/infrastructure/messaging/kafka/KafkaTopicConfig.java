package com.ricardo.scalable.ecommerce.platform.userService.infrastructure.messaging.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    NewTopic userRegisteredTopic() {
        return TopicBuilder.name("user-registered")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    NewTopic userBirthdayTopic() {
        return TopicBuilder.name("user-birthday")
                .partitions(1)
                .replicas(1)
                .build();
    }

}
