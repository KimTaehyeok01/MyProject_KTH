package com.study.studentAdmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class StudentAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentAdminApplication.class, args);
	}

}
