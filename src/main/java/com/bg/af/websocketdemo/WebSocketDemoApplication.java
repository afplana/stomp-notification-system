package com.bg.af.websocketdemo;

import org.apache.tomcat.websocket.server.WsSci;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class WebSocketDemoApplication extends SpringBootServletInitializer {

	@Bean
	public TomcatContextCustomizer tomcatContextCustomizer() {
		return context -> context.addServletContainerInitializer(new WsSci(), null);
	}

	public static void main(String[] args) {
		SpringApplication.run(WebSocketDemoApplication.class, args);
	}

}
