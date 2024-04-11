package com.example.userservicepius;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@EnableConfigurationProperties
@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
        var config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(
                List.of(

                        "http://localhost:3000",
                        "http://localhost:3000/",
                        "http://localhost:3001",
                        "http://localhost:8080",
                        "http://25.60.201.230:3000",
                        "https://web.postman.co/",
                        "http://localhost:12975",
                        "https://fe2c-178-176-75-203.ngrok-free.app",
                        "https://1dab-46-138-0-194.ngrok-free.app"
                )
        ); //УБИРАТЬ СЛЭШ В КОНЦЕ ССЫЛКИ НА NGROK!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        config.setAllowedMethods(List.of("GET", "HEAD", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("Content-Types", "Content-Type", "Accept-Encoding", "Connection", "Accept", "Content-Length", "Host",
                "authorization", "User-Agent", "cache-control","postman-token", "x-auth-token"));
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        var bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}