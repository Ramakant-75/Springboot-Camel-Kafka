package com.example.publisher.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Configuration
@Service
@Slf4j
public class KafkaTopicConfig {

    @Autowired
    private Map<String,Object> kafkaProperties;

    public void createTopic(String topicName,int partitions,short replicationFactor){
        try {
            kafkaProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
            AdminClient adminClient = AdminClient.create(kafkaProperties);
            NewTopic newTopic = new NewTopic(topicName,partitions,replicationFactor);
            adminClient.createTopics(Collections.singleton(newTopic)).all().get();
            System.out.println("Topic " + topicName + " created successfully");
        } catch (Exception e) {
            System.err.println("Error creating topic " + topicName + " : " + e.getMessage());
        }
    }

    public void deleteTopic(String topicName){

        try {
            kafkaProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
            AdminClient adminClient = AdminClient.create(kafkaProperties);
            DeleteTopicsResult deleteTopic = adminClient.deleteTopics(Collections.singletonList(topicName));
            deleteTopic.all().get();
            System.out.println("Topic " + topicName + " deleted successfully.");
        } catch (Exception e) {
            System.err.println("Error deleting topic " + topicName + ": " + e.getMessage());
        }
    }
}
