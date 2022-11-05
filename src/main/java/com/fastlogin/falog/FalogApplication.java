package com.fastlogin.falog;

import com.fastlogin.falog.model.Url;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class FalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(FalogApplication.class, args);
	}
}
