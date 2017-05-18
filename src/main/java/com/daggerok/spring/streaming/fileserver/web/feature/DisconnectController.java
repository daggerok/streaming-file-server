package com.daggerok.spring.streaming.fileserver.web.feature;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.lang.String.format;

@Controller
@RequiredArgsConstructor
public class DisconnectController {

    final RestTemplate restTemplate;

    @SneakyThrows
    @GetMapping("/disconnect")
    public void search(HttpServletRequest request, HttpServletResponse response) {

        val url = format("http://%s:%d/%s/connect/facebook",
                         request.getServerName(), request.getServerPort(), request.getContextPath());

        restTemplate.delete(url);
        response.sendRedirect(request.getContextPath().concat("/"));
    }
}
