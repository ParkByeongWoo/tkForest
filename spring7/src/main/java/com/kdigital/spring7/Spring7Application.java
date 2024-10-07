package com.kdigital.spring7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Spring7Application {

	public static void main(String[] args) {
		SpringApplication.run(Spring7Application.class, args);
	}

}
