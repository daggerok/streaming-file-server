package com.daggerok.spring.streaming.fileserver.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.util.Optional;

@Component
public class WebInterceptor implements WebRequestInterceptor {

    @Value("${app.upload.url}")
    String uploadUrl;

    @Value("${app.download.url}")
    String downloadUrl;

    @Value("${app.github.url}")
    String githubUrl;

    @Autowired
    ServletContext servletContext;

    private static final ModelMap modelMap = new ModelMap();

    @PostConstruct
    public void setUp() {
        final String ctx = servletContext.getContextPath();

        modelMap.put("ctx", ctx);
        modelMap.put("webjars", ctx.concat("/webjars"));
        modelMap.put("downloadUrl", downloadUrl);
        modelMap.put("uploadUrl", uploadUrl);
        modelMap.put("githubUrl", githubUrl);
    }

    @Override
    public void preHandle(WebRequest request) throws Exception {}

    @Override
    public void postHandle(WebRequest request, ModelMap model) throws Exception {
        Optional.ofNullable(model).ifPresent(m -> m.putAll(modelMap));
    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) throws Exception {}
}
