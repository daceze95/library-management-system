package com.dacdigitals.librarymanagementsystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http" + "://localhost:5173/")
               .allowedMethods("GET", "POST", "PUT",
             "DELETE", "PATCH").allowCredentials(true);
    }
}
