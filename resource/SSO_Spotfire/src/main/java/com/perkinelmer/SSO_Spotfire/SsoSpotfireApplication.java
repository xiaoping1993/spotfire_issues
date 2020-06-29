package com.perkinelmer.SSO_Spotfire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages ={"com.perkinelmer.SSO_Spotfire"})
public class SsoSpotfireApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SsoSpotfireApplication.class, args);
	}

}
