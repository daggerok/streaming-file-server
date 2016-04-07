package com.daggerok.spring.streaming.fileserver.web.feature;

import com.daggerok.spring.streaming.fileserver.web.annotation.Get;
import com.daggerok.spring.streaming.fileserver.web.annotation.WebPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

import static com.daggerok.spring.streaming.fileserver.web.IndexPage.REDIRECT_INDEX;
import static java.lang.String.format;

@WebPage("/logout")
public class LogoutResource {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${server.port:8080}")
    private int port;

    @Get
    public String search(HttpServletRequest request) {

        restTemplate.delete(
                format("http://localhost:%d/%s/%s", port, request.getContextPath(), "connect/facebook"));

        return REDIRECT_INDEX;
    }
}
