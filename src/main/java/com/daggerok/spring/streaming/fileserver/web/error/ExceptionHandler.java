package com.daggerok.spring.streaming.fileserver.web.error;

import io.vavr.control.Try;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class ExceptionHandler implements ErrorController {

  @Value("${error.path:/error}")
  String errorPath;

  @Override
  public String getErrorPath() {
    return errorPath;
  }

  @SneakyThrows
  @GetMapping("/error")
  public void error(final HttpServletRequest request,
                    final HttpServletResponse response,
                    final Exception e) {

    val message = Try.of(e::getMessage)
                     .getOrElse(() -> "no message");

    log.error("unexpected error: {}", message, e);
    // fix fb redirect error: just go to home page...
    response.sendRedirect(request.getContextPath().concat("/"));
  }
}
