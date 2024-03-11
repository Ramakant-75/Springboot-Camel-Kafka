package com.example.publisher.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_registration")
@Data
public class UserRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "\"user\"")
    private String user;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
}
