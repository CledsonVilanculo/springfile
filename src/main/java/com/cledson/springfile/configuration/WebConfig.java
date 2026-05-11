package com.cledson.springfile.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Mapeia o URL http://localhost:8080/api/files/** 
        // para ler os ficheiros reais em C:/files/
        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:///C:/springfile/files/");
    }
}