package com.example.publisher.controller;

import com.example.publisher.service.KafkaProducerService;
import com.example.publisher.service.UserRegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.*;

import static com.example.publisher.constant.Constant.CHATROUTE;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class MessageController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private UserRegistrationService userRegistrationService;

    @GetMapping("/publish")
    public ResponseEntity<String> publish(@RequestParam String message){
        kafkaProducerService.sendMessage(message);
        return ResponseEntity.ok("Message sent to topic");
    }

    @PostMapping("/chat")
    @CrossOrigin
    public String chatMessage(@RequestBody String message,@RequestHeader("username") String username,@RequestHeader("sendTo") String sendTo){

        log.info("called /chat API --->");
        if (userRegistrationService.usernameExist(username)){
            //TODO
            return (String) producerTemplate.requestBodyAndHeader(CHATROUTE, message,"sendTo",sendTo);
        }
        else {
            return "New User ? Please sign up first";
        }
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<String> handleMissingHeaders(MissingRequestHeaderException e){

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("please provide username & sendTo headers");
    }
}
