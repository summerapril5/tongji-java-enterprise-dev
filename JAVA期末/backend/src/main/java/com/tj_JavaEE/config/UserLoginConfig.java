package com.tj_JavaEE.config;

import com.tj_JavaEE.interceptor.UserLoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class UserLoginConfig implements WebMvcConfigurer {

    @Autowired
    private UserLoginCheckInterceptor userLoginCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginCheckInterceptor)
                //下方第一行不加注释是拦截所有请求，第二行不加注释是不拦截所有请求
                //.addPathPatterns("/**")
                .excludePathPatterns("/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/identity")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/admin/register")
                .excludePathPatterns(HttpMethod.OPTIONS.name())
                .excludePathPatterns("/user/register")
                .excludePathPatterns("/comment/{postId}");


    }

}
