package com.daggerok.spring.streaming.fileserver.config.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.util.Optional;

@Slf4j
@Component
public class WebInterceptor implements WebRequestInterceptor {

    private static final ModelMap modelMap = new ModelMap();

    @Value("${app.upload.url}")
    String uploadUrl;

    @Value("${app.download.url}")
    String downloadUrl;

    @Value("${app.github.url}")
    String githubUrl;

    @Autowired
    ServletContext servletContext;

    @Autowired
    ConnectionRepository connectionRepository;

    @Autowired
    Facebook facebook;

    @PostConstruct
    public void setUp() {
        val ctx = servletContext.getContextPath();

        modelMap.put("ctx", ctx);
        modelMap.put("webjars", ctx.concat("/webjars"));
        modelMap.put("downloadUrl", downloadUrl);
        modelMap.put("uploadUrl", uploadUrl);
        modelMap.put("githubUrl", githubUrl);
    }

    @Override
    public void preHandle(WebRequest request) throws Exception {
    }

    @Override
    public void postHandle(WebRequest request, ModelMap model) throws Exception {
        Optional.ofNullable(model).ifPresent(m -> {
            m.putAll(modelMap);

            Optional.ofNullable(connectionRepository.findPrimaryConnection(Facebook.class))
                    .ifPresent(user -> m.put("user", user));
        });
    }

    @SneakyThrows
    private void logJson(Object obj) {
        if (null == obj) return;

        log.info("{}\n{}", obj.getClass().getSimpleName(), new ObjectMapper().writeValueAsString(obj));
    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) throws Exception {
    }
}
