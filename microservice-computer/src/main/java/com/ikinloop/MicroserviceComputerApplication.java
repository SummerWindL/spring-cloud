package com.ikinloop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MicroserviceComputerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceComputerApplication.class, args);
    }

}
