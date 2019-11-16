package com.innso.serviceClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ServiceClientApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ServiceClientApplication.class, args);
	}
}
