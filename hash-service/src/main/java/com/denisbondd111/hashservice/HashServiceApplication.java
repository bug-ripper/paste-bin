package com.denisbondd111.hashservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJdbcRepositories
@EnableScheduling
public class HashServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HashServiceApplication.class, args);
    }

}
