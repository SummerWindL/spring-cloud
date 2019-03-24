package com.ikinloop.centercomfigclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CenterComfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(CenterComfigClientApplication.class, args);
    }

}
