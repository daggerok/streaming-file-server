package daggerok.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
public class CorsWebFluxConfigurer implements WebFluxConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry corsRegistry) {
    corsRegistry.addMapping("/**")
                .allowedHeaders("x-requested-with", "authorization", "Content-Type",
                                "Authorization", "credential", "X-XSRF-TOKEN")
                .allowedMethods("GET", "PUT", "POST", "DELETE", "OPTIONS")
                .allowedOrigins("*")
                .maxAge(3600);
  }
}
