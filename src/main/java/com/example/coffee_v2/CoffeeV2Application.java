package com.example.coffee_v2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class CoffeeV2Application {

	public static void main(String[] args) {
		SpringApplication.run(CoffeeV2Application.class, args);
	}

}
