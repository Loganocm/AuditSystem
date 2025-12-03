package com.project1.auditsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.project1.auditsystem", "com.portfolio.voyagevista"})
@EnableJpaRepositories(basePackages = {"com.portfolio.voyagevista.repository"})
public class AuditSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuditSystemApplication.class, args);
    }

}
