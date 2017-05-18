package com.daggerok.spring.streaming.fileserver.web.feature.disconnect;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.lang.String.format;

@RestController
@RequiredArgsConstructor
public class DisconnectResource {

    final RestTemplate restTemplate;

    @SneakyThrows
    @GetMapping("/disconnect")
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
