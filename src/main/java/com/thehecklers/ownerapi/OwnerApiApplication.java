package com.thehecklers.ownerapi;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@EnableDiscoveryClient
@SpringBootApplication
public class OwnerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OwnerApiApplication.class, args);
	}

	@Bean
	WebClient client() {
		return WebClient.create("http://customers-service/owners");
	}
}

@RestController
@AllArgsConstructor
class OwnerAPIController {
	private final WebClient client;

	@GetMapping
	String greeting() {
		return "Greetings from the Owner/Customer API app!";
	}

	@GetMapping("/owners")
	Flux<String> getOwners() {
		return client.get()
				.retrieve()
				.bodyToFlux(String.class);
	}

	@GetMapping("/owner")
	Mono<String> getOwner() {
		return client.get()
				.retrieve()
				.bodyToFlux(String.class)
				.next();
	}
}