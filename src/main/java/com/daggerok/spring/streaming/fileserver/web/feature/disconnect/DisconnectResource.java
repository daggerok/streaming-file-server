package com.daggerok.spring.streaming.fileserver.web.feature.disconnect;

import com.daggerok.spring.streaming.fileserver.web.annotation.Get;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.lang.String.format;

@RestController
public class DisconnectResource {

    @Autowired
    RestTemplate restTemplate;

    @SneakyThrows
    @Get("/disconnect")
    public void search(HttpServletRequest request, HttpServletResponse response) {

        restTemplate.delete(
                format("http://%s:%d/%s/%s",
                        request.getServerName(),
                        request.getServerPort(),
                        request.getContextPath(),
                        "connect/facebook"));

        response.sendRedirect(request.getContextPath().concat("/"));
    }
}
