package com.ikinloop.reace;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@EnableEurekaServer
@SpringBootApplication
public class SpringCloudApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringCloudApplication.class).web(true).run(args);
        //SpringApplication.run(SpringCloudApplication.class, args);

    }

}
