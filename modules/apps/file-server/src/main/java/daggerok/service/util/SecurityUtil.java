package daggerok.service.util;

import io.vavr.collection.HashMap;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class SecurityUtil {

  public static final String displayName = "displayName";
  public static final String anonymousFriendlyName = "Friend";

  private static final String profileUrlKey = "profileUrl";
  private static final String profileUrlValue = "api/v1/users/profile";

  public static Map<String, String> displayName() {
    return HashMap.of(profileUrlKey, profileUrlValue,
                      displayName, Option.of(SecurityContextHolder.getContext())
                                         .map(SecurityContext::getAuthentication)
                                         .map(auth -> (auth instanceof UserDetails)
                                             ? ((UserDetails) auth).getUsername()
                                             : auth.getName())
                                         .getOrElse(() -> anonymousFriendlyName))
                  .toJavaMap();
  }

  public static void disconnect(HttpServletResponse resp) {
    Try.run(() -> SecurityContextHolder.getContext()
                                       .getAuthentication()
                                       .setAuthenticated(false))
       .onFailure(e -> log.error("disconnection error: {}", e.getLocalizedMessage(), e));

    log.debug("user is authenticated: {}", SecurityContextHolder.getContext()
                                                                .getAuthentication()
                                                                .isAuthenticated());
  }
}
