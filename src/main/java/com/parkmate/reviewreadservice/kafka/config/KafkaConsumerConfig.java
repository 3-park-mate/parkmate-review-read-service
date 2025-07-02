package com.parkmate.reviewreadservice.kafka.config;

import com.parkmate.reviewreadservice.kafka.event.*;
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
    public ConsumerFactory<String, ReviewCreatedEvent> createReviewEventConsumerFactory() {
        JsonDeserializer<ReviewCreatedEvent> deserializer = new JsonDeserializer<>(ReviewCreatedEvent.class, false);
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
    public ConcurrentKafkaListenerContainerFactory<String, ReviewCreatedEvent> createReviewEventKafkaListener() {
        ConcurrentKafkaListenerContainerFactory<String, ReviewCreatedEvent> factory =
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
    public ConsumerFactory<String, UserProfileUpdatedEvent> updateUserProfileEventConsumerFactory() {
        JsonDeserializer<UserProfileUpdatedEvent> deserializer = new JsonDeserializer<>(UserProfileUpdatedEvent.class, false);
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
    public ConcurrentKafkaListenerContainerFactory<String, UserProfileUpdatedEvent> updateUserProfileEventKafkaListener() {
        ConcurrentKafkaListenerContainerFactory<String, UserProfileUpdatedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(updateUserProfileEventConsumerFactory());
        return factory;
    }
    @Bean
    public ConsumerFactory<String, ReviewReactionUpdatedEvent> reviewReactionUpdatedEventConsumerFactory() {
        JsonDeserializer<ReviewReactionUpdatedEvent> deserializer =
                new JsonDeserializer<>(ReviewReactionUpdatedEvent.class, false);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.setUseTypeMapperForKey(false);
        deserializer.addTrustedPackages("*");

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "review-read.reaction-updated");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean(name = "reviewReactionUpdatedEventKafkaListener")
    public ConcurrentKafkaListenerContainerFactory<String, ReviewReactionUpdatedEvent> reviewReactionUpdatedEventKafkaListener() {
        ConcurrentKafkaListenerContainerFactory<String, ReviewReactionUpdatedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(reviewReactionUpdatedEventConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, ReviewUpdatedEvent> reviewUpdatedEventConsumerFactory() {
        JsonDeserializer<ReviewUpdatedEvent> deserializer =
                new JsonDeserializer<>(ReviewUpdatedEvent.class, false);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.setUseTypeMapperForKey(false);
        deserializer.addTrustedPackages("*");

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "review-read.review-updated");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean(name = "reviewUpdatedEventKafkaListener")
    public ConcurrentKafkaListenerContainerFactory<String, ReviewUpdatedEvent> reviewUpdatedEventKafkaListener() {
        ConcurrentKafkaListenerContainerFactory<String, ReviewUpdatedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(reviewUpdatedEventConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, ReviewDeletedEvent> reviewDeletedEventConsumerFactory() {
        JsonDeserializer<ReviewDeletedEvent> deserializer =
                new JsonDeserializer<>(ReviewDeletedEvent.class, false);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.setUseTypeMapperForKey(false);
        deserializer.addTrustedPackages("*");

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "review-read.review-deleted");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean(name = "reviewDeletedEventKafkaListener")
    public ConcurrentKafkaListenerContainerFactory<String, ReviewDeletedEvent> reviewDeletedEventKafkaListener() {
        ConcurrentKafkaListenerContainerFactory<String, ReviewDeletedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(reviewDeletedEventConsumerFactory());
        return factory;
    }
}