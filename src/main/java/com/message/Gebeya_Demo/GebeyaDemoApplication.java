package com.message.Gebeya_Demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GebeyaDemoApplication {

	
	
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(GebeyaDemoApplication.class);
		springApplication.setAdditionalProfiles("prod");
		springApplication.run(args);
		//SpringApplication.run(GebeyaDemoApplication.class, args);
		
		
	}

}
