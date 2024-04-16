package com.example.construction.config1;

//@Configuration
//@EnableWebMvcimplements WebMvcConfigurer
public class CorsConfig  {

    /*@Override
    public void addCorsMappings(CorsRegistry registry) {
        //WebMvcConfigurer.super.addCorsMappings(registry);
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200", "http://localhost:4200/")
                .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS", "HEAD")
                .allowedHeaders("Authorization","Content-Type","x-auth-token")
                .allowCredentials(true)
                .allowedHeaders("x-auth-token","X-Get-Header")
                .maxAge(3000L);
    }*/
}
