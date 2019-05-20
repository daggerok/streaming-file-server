package daggerok.web.config;

import daggerok.config.props.AppProps;
import io.vavr.collection.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.util.Map;

import static daggerok.service.util.SecurityUtil.displayName;
import static java.util.Optional.ofNullable;

@Component
@RequiredArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class WebInterceptor implements WebRequestInterceptor {

  private final AppProps app;
  private final ServletContext servletContext;

  private Map<String, String> modelMap;

  @PostConstruct
  public void postConstruct() {
    val ctx = servletContext.getContextPath();
    modelMap = HashMap.of("ctx", ctx,
                          "downloadUrl", app.getDownload().getUrl(),
                          "uploadUrl", app.getUpload().getUrl(),
                          "githubUrl", app.getGithub().getUrl(),
                          "profileUrl", "/api/v1/users/profile")
                      .toJavaMap();
  }

  @Override
  public void preHandle(WebRequest request) throws Exception { }

  @Override
  public void postHandle(final WebRequest request, @Nullable final ModelMap model) throws Exception {
    ofNullable(model).ifPresent(m -> {
      m.putAll(modelMap);
      m.put("user", displayName());
    });
  }

  @Override
  public void afterCompletion(WebRequest request, @Nullable Exception ex) throws Exception { }
}
