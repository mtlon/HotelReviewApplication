package com.api.hotelreviewapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class HotelReviewApplication {
    public static void main(String[] args) {
        SpringApplication.run(HotelReviewApplication.class, args);
    }
}
