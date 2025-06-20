package com.parkmate.reviewreadservice.kafka.config;

import com.parkmate.reviewreadservice.kafka.event.CreateReviewEvent;
import com.parkmate.reviewreadservice.kafka.event.CreateReviewJoinUserEvent;
import com.parkmate.reviewreadservice.kafka.event.UpdateUserProfileEvent;
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
import java.util.HashMap;
import java.util.Map;;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ConsumerFactory<String, CreateReviewEvent> createReviewEventConsumerFactory() {
        JsonDeserializer<CreateReviewEvent> deserializer = new JsonDeserializer<>(CreateReviewEvent.class, false);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.setUseTypeMapperForKey(false);
        deserializer.addTrustedPackages("*");

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "review-read.create-review");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean(name = "createReviewEventKafkaListener")
    public ConcurrentKafkaListenerContainerFactory<String, CreateReviewEvent> createReviewEventKafkaListener() {
        ConcurrentKafkaListenerContainerFactory<String, CreateReviewEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(createReviewEventConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, CreateReviewJoinUserEvent> createReviewJoinUserEventConsumerFactory() {
        JsonDeserializer<CreateReviewJoinUserEvent> deserializer = new JsonDeserializer<>(CreateReviewJoinUserEvent.class, false);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.setUseTypeMapperForKey(false);
        deserializer.addTrustedPackages("*");

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "review-read.create-review-join-user");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean(name = "createReviewJoinUserEventKafkaListener")
    public ConcurrentKafkaListenerContainerFactory<String, CreateReviewJoinUserEvent> createReviewJoinUserEventKafkaListener() {
        ConcurrentKafkaListenerContainerFactory<String, CreateReviewJoinUserEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(createReviewJoinUserEventConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, UpdateUserProfileEvent> updateUserProfileEventConsumerFactory() {
        JsonDeserializer<UpdateUserProfileEvent> deserializer = new JsonDeserializer<>(UpdateUserProfileEvent.class, false);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.setUseTypeMapperForKey(false);
        deserializer.addTrustedPackages("*");

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "update-user-profile-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean(name = "updateUserProfileEventKafkaListener")
    public ConcurrentKafkaListenerContainerFactory<String, UpdateUserProfileEvent> updateUserProfileEventKafkaListener() {
        ConcurrentKafkaListenerContainerFactory<String, UpdateUserProfileEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(updateUserProfileEventConsumerFactory());
        return factory;
    }
}