package com.spring.bootexample.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@Configuration
public class DevSpecificConfiguration {
    @Bean
    public String cache() {
        return "Dev Cache Configuration";
    }
}