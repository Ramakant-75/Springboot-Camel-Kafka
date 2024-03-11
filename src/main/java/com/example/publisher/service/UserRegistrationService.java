package com.example.publisher.service;

import com.example.publisher.dao.UserRegistrationRepository;
import com.example.publisher.entity.UserRegistration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class UserRegistrationService {

    @Autowired
    private UserRegistrationRepository userRegistrationRepository;

    public boolean usernameExist(String username){
        boolean userExist = false;
        UserRegistration user = userRegistrationRepository.getUserDetails(username);
        if (!ObjectUtils.isEmpty(user)){
            userExist = true;
        }

        return userExist;
    }

    public void registerUser(UserRegistration user) {

        try {
            userRegistrationRepository.save(user);
        }catch (Exception e){
            log.error("Error while saving user data : " + e.getMessage());
        }
    }

    public List<UserRegistration> getUserDetails(String username) {
        List<UserRegistration> userRegistrationList = new ArrayList<>();

        userRegistrationList.add(userRegistrationRepository.getUserDetails(username));

        return userRegistrationList;
    }

    public List<UserRegistration> getAllUserDetails() {

        return userRegistrationRepository.findAll();
    }
}
