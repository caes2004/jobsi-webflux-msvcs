package com.escaes.ms_eureka_jobsi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MsEurekaJobsiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsEurekaJobsiApplication.class, args);
	}

}
