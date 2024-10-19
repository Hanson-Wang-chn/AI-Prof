package com.example.ologyprofbackenddemo.common;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Configuration
@Component
@ConfigurationProperties("app")
public class AppProperties {
    private String publicKey;
}
