package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    // Spring'in ObjectMapper'ı tanıması için bu Bean'i ekliyoruz
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}