package com.nkhank11.microservices.product;

import org.springframework.boot.SpringApplication;

public class TestProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(ProductServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
