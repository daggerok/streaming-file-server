package daggerok.config;

import daggerok.config.web.WebInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@RequiredArgsConstructor
public class MustacheCfg extends WebMvcConfigurerAdapter {

  final WebInterceptor webInterceptor;

  @Override
  public void addInterceptors(final InterceptorRegistry registry) {
    super.addInterceptors(registry);
    registry.addWebRequestInterceptor(webInterceptor);
  }
}
