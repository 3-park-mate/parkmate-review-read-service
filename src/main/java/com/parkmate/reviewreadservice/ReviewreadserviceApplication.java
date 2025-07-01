package com.parkmate.reviewreadservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.parkmate.reviewreadservice.reviewread.infrastructure")
@EnableDiscoveryClient
@SpringBootApplication
public class ReviewreadserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewreadserviceApplication.class, args);
	}

}
