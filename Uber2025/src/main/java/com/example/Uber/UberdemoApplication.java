package com.example.Uber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = {"com.example.Uber"})
public class UberdemoApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(UberdemoApplication.class, args);

		System.out.println("GrpcClient bean exists? " + context.containsBean("grpcClient"));
	}
}

