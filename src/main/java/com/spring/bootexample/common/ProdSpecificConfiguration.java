package com.spring.bootexample.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("prod")
@Configuration
public class ProdSpecificConfiguration {
    @Bean
    public String cache() {
        return "Production Cache Configuration - Distributed Cache";
    }
}