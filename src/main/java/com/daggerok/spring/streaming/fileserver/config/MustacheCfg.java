package com.daggerok.spring.streaming.fileserver.config;

import com.daggerok.spring.streaming.fileserver.config.web.WebInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
public class MustacheCfg extends WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter {

    @Autowired
    WebInterceptor webInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        super.addInterceptors(registry);

        registry.addWebRequestInterceptor(webInterceptor);
    }
}
