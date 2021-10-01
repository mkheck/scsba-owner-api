package com.thehecklers.ownerapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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
		return "Greetings from the Owner API app!";
	}

	@GetMapping("/owners")
	Flux<OwnerDetails> getOwners() {
		return client.get()
				.retrieve()
				.bodyToFlux(OwnerDetails.class);
	}

	@GetMapping("/owner")
	Mono<OwnerDetails> getOneOwner() {
		return client.get()
				.retrieve()
				.bodyToFlux(OwnerDetails.class)
				.next();
	}
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class OwnerDetails {
	private String firstName, lastName, address, city, telephone;
}
