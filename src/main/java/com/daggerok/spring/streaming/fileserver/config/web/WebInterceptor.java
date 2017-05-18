package com.daggerok.spring.streaming.fileserver.config.web;

import com.daggerok.spring.streaming.fileserver.config.props.AppProps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import static java.util.Optional.ofNullable;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebInterceptor implements WebRequestInterceptor {

    private static final ModelMap modelMap = new ModelMap();

    final AppProps app;
    final ServletContext servletContext;
    final ConnectionRepository connectionRepository;

    @PostConstruct
    public void setUp() {

        val ctx = servletContext.getContextPath();

        modelMap.put("ctx", ctx);
        modelMap.put("downloadUrl", app.download.url);
        modelMap.put("uploadUrl", app.upload.url);
        modelMap.put("githubUrl", app.github.url);
    }

    @Override
    public void preHandle(WebRequest request) throws Exception {}

    @Override
    public void postHandle(WebRequest request, ModelMap model) throws Exception {

        ofNullable(model).ifPresent(m -> {

            m.putAll(modelMap);

            val user = connectionRepository.findPrimaryConnection(Facebook.class);

            ofNullable(user)
                .ifPresent(u -> m.put("user", u));
        });
    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) throws Exception {}
}
