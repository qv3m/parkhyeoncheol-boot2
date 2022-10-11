package com.herohuapp.parkhyeoncheolboot2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing
@SpringBootApplication//(exclude = SecurityAutoConfiguration.class)
public class ParkhyeoncheolBoot2Application {

	public static void main(String[] args) {
		SpringApplication.run(ParkhyeoncheolBoot2Application.class, args);
	}

}
