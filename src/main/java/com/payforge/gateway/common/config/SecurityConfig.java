package com.payforge.gateway.common.config;

import com.payforge.gateway.common.security.ApiKeyAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Bean
    public FilterRegistrationBean<ApiKeyAuthenticationFilter>
    apiKeyFilter(
            ApiKeyAuthenticationFilter filter) {

        FilterRegistrationBean<ApiKeyAuthenticationFilter>
                registration =
                new FilterRegistrationBean<>();

        registration.setFilter(filter);

        registration.addUrlPatterns("/*");

        registration.setOrder(1);

        return registration;
    }
}