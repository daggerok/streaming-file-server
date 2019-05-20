package daggerok.config;

import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

//@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsWebFluxCfg {

  private static final String ALLOWED_HEADERS = "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN";
  private static final String ALLOWED_METHODS = "GET, PUT, POST, DELETE, OPTIONS";
  private static final String ALLOWED_ORIGIN = "*";
  private static final long MAX_AGE = 3600;

  @Bean
  public CorsWebFilter corsWebFilter() {
    val corsConfig = new CorsConfiguration();
    corsConfig.addAllowedOrigin(ALLOWED_ORIGIN);
    corsConfig.addAllowedMethod(ALLOWED_METHODS);
    corsConfig.addAllowedHeader(ALLOWED_HEADERS);
    corsConfig.setMaxAge(MAX_AGE);

    val source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfig);

    return new CorsWebFilter(source);
  }
}
