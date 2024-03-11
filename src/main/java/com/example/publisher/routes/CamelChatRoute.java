package com.example.publisher.routes;

import com.example.publisher.processor.ChatProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static com.example.publisher.constant.Constant.CHATROUTE;
import static com.example.publisher.constant.Constant.KAFKAROUTE;

@Component
@Slf4j
public class CamelChatRoute extends RouteBuilder {


    @Override
    public void configure() throws Exception {

        from(CHATROUTE)
                .id("chat-router")
                .log("started chat-router --->")
                .log("headers : ${header.sendTo}")
                .log("incoming body to chat-router : ${body}")
                .process(new ChatProcessor())
                .to(KAFKAROUTE);


        from(KAFKAROUTE)
                .id("kafka-router")
                .log("started kafka router --->")
                .setHeader("kafkaTopic",simple("${exchangeProperty.recipient}"))
                .toD("kafka:${headers[kafka.TOPIC]}"
                        + "?brokers=localhost:9092&groupId=myGroup")
                .log("message sent to ${exchangeProperty.recipient} : ${body}");
    }
}
