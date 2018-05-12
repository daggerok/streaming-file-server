package daggerok.web.feature;

import daggerok.service.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DisconnectController {

  final RestTemplate restTemplate;

  @SneakyThrows
  @GetMapping("/disconnect") // TODO...
  public void search(final HttpServletRequest req, final HttpServletResponse resp) {
/*
    val url = UriComponentsBuilder.fromHttpUrl(format("%s://%s:%d", protocol, req.getServerName(), req.getServerPort()))
                                  .path(uri)
                                  .build()
                                  .toString();

    Try.run(() -> restTemplate.delete(url))
       .onFailure(throwable -> log.error("disconnect controller error: {}",
                                         throwable.getLocalizedMessage(), throwable));
*/
    SecurityUtil.disconnect();

    resp.sendRedirect(req.getContextPath().concat("/"));
  }
}
