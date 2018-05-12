package daggerok.web.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class MustacheCfg implements WebMvcConfigurer {

  final WebInterceptor webInterceptor;

  @Override
  public void addInterceptors(final InterceptorRegistry registry) {
    registry.addWebRequestInterceptor(webInterceptor);
  }
}
