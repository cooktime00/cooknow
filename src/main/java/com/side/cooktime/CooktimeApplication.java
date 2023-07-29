package com.side.cooktime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CooktimeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CooktimeApplication.class, args);
	}

}
