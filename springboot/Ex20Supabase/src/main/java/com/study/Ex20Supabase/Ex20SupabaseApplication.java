package com.study.Ex20Supabase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Ex20SupabaseApplication {

	public static void main(String[] args) {
		System.setProperty("java.net.preferIPv6Addresses", "true");
		SpringApplication.run(Ex20SupabaseApplication.class, args);
	}

}
