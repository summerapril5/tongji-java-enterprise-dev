package com.tj_JavaEE.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 配置全局 CORS，允许来自 http://localhost:8080 的请求
        registry.addMapping("/**")  // 允许所有请求路径
                .allowedOrigins("http://localhost:8088")  // 允许的来源域
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 允许的方法
                .allowedHeaders("*")  // 允许的请求头
                .allowCredentials(true)  // 允许带有凭证的请求（如 cookies）
                .maxAge(3600);  // 设置预检请求的缓存时间
    }
}
