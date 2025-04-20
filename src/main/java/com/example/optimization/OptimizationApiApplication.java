package com.example.optimization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class OptimizationApiApplication {

    private static final Logger logger = LoggerFactory.getLogger(OptimizationApiApplication.class);

    public static void main(String[] args) {
        // Launch the Spring Boot application and log the startup
        SpringApplication.run(OptimizationApiApplication.class, args);
        logger.info("Optimization API application has started successfully.");
    }
}
