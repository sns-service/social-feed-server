package com.example.feedserver;

import com.example.feedserver.config.JacksonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(JacksonConfiguration.class)
public class FeedServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeedServerApplication.class, args);
	}

}
