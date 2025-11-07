package dev.felipe.climaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ClimaApiApplication
{
    public static void main(String[] args) {
        SpringApplication.run(ClimaApiApplication.class, args);
    }
}

