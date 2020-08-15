package com.bg.af.websocketdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class WebSocketDemoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(WebSocketDemoApplication.class, args);
	}

}
