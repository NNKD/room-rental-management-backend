package com.roomrentalmanagementbackend.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${cors.allowed-origins}")
    private String[] allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if (Arrays.asList(allowedOrigins).contains("*")) {
            registry.addMapping("/**")
                    .allowedOrigins("*")
                    .allowCredentials(false)
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*");
        }else {
            registry.addMapping("/**")
                    .allowedOrigins(allowedOrigins)
                    .allowCredentials(true)
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*");

        }
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
