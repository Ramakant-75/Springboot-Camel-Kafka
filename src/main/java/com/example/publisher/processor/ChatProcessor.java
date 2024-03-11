package com.example.publisher.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.kafka.KafkaConstants;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class ChatProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String message = String.valueOf(exchange.getIn().getBody());
        log.info("message body in chat processor : {}", message);
        String recipient = exchange.getIn().getHeader("sendTo",String.class);
        exchange.getIn().setBody(message +", at " + new Date());
        log.info("recipient : " + recipient);
        exchange.setProperty("recipient",recipient);
        exchange.getIn().setHeader(KafkaConstants.TOPIC,recipient);
    }
}
