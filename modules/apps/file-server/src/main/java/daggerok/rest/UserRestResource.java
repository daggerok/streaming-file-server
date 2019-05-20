package daggerok.rest;

import io.vavr.collection.HashMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

import static daggerok.service.util.SecurityUtil.displayName;

@RestController
public class UserRestResource {

  @GetMapping("/api/v1/users/profile")
  public Map getUserProfile(final Principal principal) {
    return HashMap.of("user", displayName(),
                      "principal", principal)
                  .toJavaMap();
  }
}
