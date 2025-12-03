package com.project1.auditsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.project1.auditsystem", "com.portfolio.voyagevista"})
public class AuditSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuditSystemApplication.class, args);
    }

}
