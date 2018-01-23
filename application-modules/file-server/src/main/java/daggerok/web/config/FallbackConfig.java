package daggerok.web.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class FallbackConfig {

  @Bean
  public ErrorPageRegistrar errorPageRegistrar() {
    return new MyErrorPageRegistrar();
  }

  private static class MyErrorPageRegistrar implements ErrorPageRegistrar {

    @Override
    public void registerErrorPages(final ErrorPageRegistry registry) {
      registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
    }

  }

  @Slf4j
  @Controller
  public static class FallbackController {

    @GetMapping({ "/404" })
    String fallback(final HttpServletRequest request) {
      if (log.isDebugEnabled()) log.debug("'{}' -> '/'", request.getRequestURI());
      return "redirect:/";
    }
  }
}
