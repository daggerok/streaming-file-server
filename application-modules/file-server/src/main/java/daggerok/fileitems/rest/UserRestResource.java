package daggerok.fileitems.rest;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import static daggerok.service.util.SecurityUtil.displayName;
import static java.util.Collections.singletonMap;

@RestController
@RequiredArgsConstructor
public class UserRestResource {

  @GetMapping("/api/v1/users/profile")
  public Map getUserProfile(final Principal principal) {
    val profile = new HashMap<String, Object>();
    profile.putAll(singletonMap("user", displayName()));
    profile.putAll(singletonMap("principal", principal));
    return profile;
  }
}
