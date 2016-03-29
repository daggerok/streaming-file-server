package com.daggerok.spring.streaming.fileserver.error;

import com.daggerok.spring.streaming.fileserver.web.annotation.Get;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ExceptionHandler implements ErrorController {
    @Value("${error.path:/error}")
    String errorPath;

    @Override
    public String getErrorPath() {
        return errorPath;
    }

    @SneakyThrows
    @Get("/error")
    public void error(HttpServletRequest request, HttpServletResponse response, Exception e) {
        response.sendRedirect(request.getContextPath().concat("/"));
    }
}
