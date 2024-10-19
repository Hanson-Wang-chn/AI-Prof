package com.example.ologyprofbackenddemo;

import com.example.ologyprofbackenddemo.common.AppProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class OlogyProfBackendDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(OlogyProfBackendDemoApplication.class, args);
    }
}
