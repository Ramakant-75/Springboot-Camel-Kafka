//package com.example.publisher.service;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
//@Service
//@Slf4j
//public class KafkaConsumerService {
//
//    @KafkaListener(topics = "topic-1",groupId = "myGroup")
//    public void consumer(String message, ConsumerRecord<String,String> record){
//        log.info("{} messaged you : {},{}",record.topic(),message,new Date());
//    }
//
//    @KafkaListener(topics = "chat-topic",groupId = "myGroup")
//    public void consumeChat(String message, ConsumerRecord<String,String> record){
//        log.info("{} messaged you : {},{}",record.topic(),message,new Date());
//
//    }
//}
