package com.hotel.room_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(scanBasePackages = "com.hotel")
public class RoomServiceAPIApplication {

	public static void main(String[] args) {
        //System.out.println(new BCryptPasswordEncoder().encode("123"));
		SpringApplication.run(RoomServiceAPIApplication.class, args);
	}

}
