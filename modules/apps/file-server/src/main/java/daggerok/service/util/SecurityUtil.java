package daggerok.service.util;

import io.vavr.control.Try;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class SecurityUtil {

  public static final String displayName = "displayName";
  public static final String anonymousFriendlyName = "Friend";

  private static final String profileUrlKey = "profileUrl";
  private static final String profileUrlValue = "api/v1/users/profile";
  private static final Map<String, String> result = new HashMap<>(singletonMap(profileUrlKey, profileUrlValue));

  public static Map<String, String> displayName() {

    result.put(displayName, anonymousFriendlyName);
    ofNullable(SecurityContextHolder.getContext().getAuthentication()).ifPresent(auth -> {

      if (auth instanceof AnonymousAuthenticationToken) {

        result.put(displayName, auth.getName());

      } else {

        val username = Try.of(() -> (UserDetails) SecurityContextHolder.getContext()
                                                                       .getAuthentication()
                                                                       .getPrincipal())
                          .map(UserDetails::getUsername)
                          .getOrElseGet(throwable -> null);

        ofNullable(username).ifPresent(u -> result.put(displayName, u));
      }
    });

    return result;
  }

  public static void disconnect() {

    Try.run(() -> SecurityContextHolder.getContext()
                                       .getAuthentication()
                                       .setAuthenticated(false))
       .onFailure(throwable -> log.error("disconnection error: {}", throwable.getLocalizedMessage(), throwable));
  }
}
