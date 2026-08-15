package com.newjoinerportal.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.newjoinerportal.auth.dto.ProfileResponse;
import com.newjoinerportal.auth.exception.UserNotFoundException;

@SpringBootApplication
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

}