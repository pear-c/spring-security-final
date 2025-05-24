package com.example.springsecurityfinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpringSecurityFinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityFinalApplication.class, args);
	}

}
