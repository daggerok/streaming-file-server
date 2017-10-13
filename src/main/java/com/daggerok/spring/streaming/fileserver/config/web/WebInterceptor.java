package com.daggerok.spring.streaming.fileserver.config.web;

import com.daggerok.spring.streaming.fileserver.config.props.AppProps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import java.util.Map;

import static com.daggerok.spring.streaming.fileserver.service.util.SecurityUtil.displayName;
import static java.util.Optional.ofNullable;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebInterceptor implements WebRequestInterceptor {

  static final Map modelMap = new ModelMap();

  final AppProps app;
  final ServletContext servletContext;

  @PostConstruct
  public void setUp() {

    val ctx = servletContext.getContextPath();

    modelMap.put("ctx", ctx);
    modelMap.put("downloadUrl", app.download.url);
    modelMap.put("uploadUrl", app.upload.url);
    modelMap.put("githubUrl", app.github.url);
    modelMap.put("profileUrl", "/api/v1/users/profile");
  }

  @Override
  public void preHandle(final WebRequest request) throws Exception {}

  @Override
  public void postHandle(final WebRequest request, final ModelMap model) throws Exception {
    ofNullable(model).ifPresent(m -> {
      modelMap.put("user", displayName());
      m.putAll(modelMap);
      System.out.println(m);
    });
  }

  @Override
  public void afterCompletion(final WebRequest request, final Exception ex) throws Exception {}
}
