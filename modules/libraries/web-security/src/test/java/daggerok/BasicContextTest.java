package daggerok;

import daggerok.config.WebSecurityAutoConfiguration;
import daggerok.config.security.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = NONE,
    classes = WebSecurityAutoConfiguration.class)
public class BasicContextTest {

  @Autowired
  ConfigurableApplicationContext app;

  @Test
  public void testContext() throws Exception {
    assertNotNull("bean securityConfig wasn't found.", app.getBean(SecurityConfig.class));
    assertNotNull("bean passwordEncoder wasn't found.", app.getBean(PasswordEncoder.class));
    assertNotNull("bean userDetailsService wasn't found.", app.getBean(UserDetailsService.class));
  }
}
