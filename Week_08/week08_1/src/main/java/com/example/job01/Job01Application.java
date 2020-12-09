package com.example.job01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class Job01Application {

	public static void main(String[] args) {
		SpringApplication.run(Job01Application.class, args);
	}

}
