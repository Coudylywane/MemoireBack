package com.example.construction.config1;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    public CorsConfig() {
        System.out.println("CorsConfig loaded!"); // Message de log pour vérifier le chargement
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("Configuring CORS..."); // Message de log pour vérifier l'exécution
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:4200") // Autoriser Angular
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Autoriser les méthodes HTTP
                .allowedHeaders("*") // Autoriser tous les headers
                .allowCredentials(true); // Autoriser les cookies
    }
}