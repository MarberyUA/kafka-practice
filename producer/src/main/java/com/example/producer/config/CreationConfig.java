package com.example.producer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

//Enable if topic auto-creation is needed
//@Configuration
public class CreationConfig {

    @Bean
    public NewTopic libraryEvents() {
        return TopicBuilder
                .name("library-events")
                .partitions(3)
                .replicas(3)
                .build();
    }

}
