package com.expense.tracker.play.config;

import com.expense.tracker.play.common.filters.AuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.FilterRegistration;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<AuthFilter> filterRegistrationBean(AuthFilter authFilter) {
        FilterRegistrationBean<AuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(authFilter);
        registration.addUrlPatterns("/api/categories/*");
        return registration;
    }

}
