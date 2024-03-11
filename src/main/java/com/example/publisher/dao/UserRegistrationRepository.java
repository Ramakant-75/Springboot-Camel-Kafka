package com.example.publisher.dao;

import com.example.publisher.entity.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRegistrationRepository extends JpaRepository<UserRegistration, Long> {

    @Query(value = "SELECT * FROM user_registration where username =:username",nativeQuery = true)
    public UserRegistration getUserDetails(String username);

}
