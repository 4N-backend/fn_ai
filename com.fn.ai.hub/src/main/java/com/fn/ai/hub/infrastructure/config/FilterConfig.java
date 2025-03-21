package com.fn.ai.hub.infrastructure.config;

import com.fn.ai.common.filter.SwaggerHeaderMockFilter;
import com.fn.ai.common.filter.UserContextFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<UserContextFilter> userContextFilter(){
        FilterRegistrationBean<UserContextFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UserContextFilter());
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<SwaggerHeaderMockFilter> swaggerHeaderMockFilter(){
        FilterRegistrationBean<SwaggerHeaderMockFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SwaggerHeaderMockFilter());
        registrationBean.setOrder(0);
        return registrationBean;
    }

}
