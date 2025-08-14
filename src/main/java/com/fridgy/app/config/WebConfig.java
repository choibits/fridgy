package com.fridgy.app.config;

import com.fridgy.app.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;


    @Override // "Hiring the bodyguard"
    public void addInterceptors(InterceptorRegistry registry) { // "Add employees - employee registry"
        registry.addInterceptor(jwtInterceptor) // "Add the bodyguard"
        .addPathPatterns("/**")// tell bodyguard where to open it - which doors to cover? which path? This means every end point
        .excludePathPatterns("/auth/signup", "/auth/login"); // But, exclude doors that look like this
    }
}
