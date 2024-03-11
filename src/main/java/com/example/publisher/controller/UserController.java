package com.example.publisher.controller;

import com.example.publisher.config.KafkaTopicConfig;
import com.example.publisher.entity.UserRegistration;
import com.example.publisher.service.UserRegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserRegistrationService userRegistrationService;

    @Autowired
    private KafkaTopicConfig kafkaTopicConfig;

    @Autowired
    private KafkaAdmin kafkaAdmin;

    @PostMapping("/signup")
    @CrossOrigin
    public ResponseEntity<String> registerUser(@RequestBody UserRegistration user){

        if (userRegistrationService.usernameExist(user.getUsername())){
            return new ResponseEntity<>("provided username already exists, try something else",HttpStatus.NOT_ACCEPTABLE);
        }

        else if (!isValidField(user.getPassword())){
            return new ResponseEntity<>("password should contain atleast 1 upperCase letter, 1 numeric and 1 special character",HttpStatus.NOT_ACCEPTABLE);
        }
        else {

            userRegistrationService.registerUser(user);
            kafkaTopicConfig.createTopic(user.getUsername(),2, (short) 1);

            log.info("topic created for username : {}",user.getUsername());

            return new ResponseEntity<>("User registered", HttpStatus.OK);
        }
    }

    @GetMapping("/userdetails")
    @CrossOrigin
    public ResponseEntity<List<UserRegistration>> getUserDetails(@RequestParam(required = false) String username){
        log.info("calling /userdetails API --->");
        List<UserRegistration> userDetailList = new ArrayList<>();
        if (username == null || StringUtils.isEmpty(username)) {
            userDetailList = userRegistrationService.getAllUserDetails();
        }else {
            userDetailList = userRegistrationService.getUserDetails(username);
        }
        return new ResponseEntity<>(userDetailList,HttpStatus.OK);
    }

    public static boolean isValidField(String field){
        String regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%&]).*$";
        return Pattern.compile(regex).matcher(field).matches();
    }


    //TODO - issues with deleting a topic, it shuts down the broker
    @GetMapping("/signout")
    @CrossOrigin
    public void signOut(@RequestParam String username){

        kafkaTopicConfig.deleteTopic(username);

    }
}
