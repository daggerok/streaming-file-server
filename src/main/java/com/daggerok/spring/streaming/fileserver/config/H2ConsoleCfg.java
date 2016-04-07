package com.daggerok.spring.streaming.fileserver.config;

import org.h2.server.web.WebServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class H2ConsoleCfg {

    @Bean
    public ServletRegistrationBean h2servletRegistration() {

        ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());

        registration.addUrlMappings("/h2/*");
        return registration;
    }
}
