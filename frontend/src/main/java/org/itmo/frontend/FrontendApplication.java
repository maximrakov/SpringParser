package org.itmo.frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"org.itmo.config", "org.itmo.frontend.controller"})
public class FrontendApplication {
	public static void main(String[] args) {
		SpringApplication.run(FrontendApplication.class, args);
	}
}
