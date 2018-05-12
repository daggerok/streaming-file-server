package daggerok.web.config;

import daggerok.config.props.AppProps;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.util.Map;

import static daggerok.service.util.SecurityUtil.displayName;
import static java.util.Optional.ofNullable;

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
    });
  }

  @Override
  public void afterCompletion(final WebRequest request, final Exception ex) throws Exception {}
}
