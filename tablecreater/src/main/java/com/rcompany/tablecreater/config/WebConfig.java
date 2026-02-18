package com.rcompany.tablecreater.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:3000",
                        "http://127.0.0.1:5500",
                        "http://localhost:8080"  // Add your backend domain here
                )
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("Authorization")
                .allowCredentials(true)
                .maxAge(3600);
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /uploads/** URL-ə gələn request-ləri lokal fayl sistemindəki "uploads" qovluğuna yönləndirir
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:./uploads/"); // Fayllar Spring Boot proyektinin root qovluğundakı "uploads" folderində yerləşir
    }
}