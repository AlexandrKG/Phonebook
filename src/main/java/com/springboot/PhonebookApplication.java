package com.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;



//@Configuration
//@EnableAutoConfiguration
//@ComponentScan

@SpringBootApplication
public class PhonebookApplication extends SpringBootServletInitializer{


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PhonebookApplication.class);
	}

	public static void main(String[] args) {

		SpringApplication.run(PhonebookApplication.class, args);
//		SpringApplication.run(new Class<?>[] {PhonebookApplication.class, DatabaseConfig.class}, args);
	}
}
