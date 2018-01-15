package com.bemobi.shortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class ShortenerApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ShortenerApplication.class, args);
	}
}
