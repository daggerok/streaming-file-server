package com.daggerok.spring.streaming.fileserver.error;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.Objects.nonNull;

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
    public void error(HttpServletRequest request, HttpServletResponse response, Exception e) {
        log.error(nonNull(e)? e.getMessage() : "unexpected error.", e);
        response.sendRedirect(request.getContextPath().concat("/"));
    }
}
