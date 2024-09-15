package com.perseo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = {"com.perseo.models"})
@SpringBootApplication
public class PerseoApp {

	public static void main(String[] args) {
		SpringApplication.run(PerseoApp.class, args);
	}

}
