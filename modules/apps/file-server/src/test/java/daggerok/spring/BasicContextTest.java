package daggerok.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.GenericApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasicContextTest {

  @Autowired
  GenericApplicationContext app;

  @Test
  public void test() throws Exception {
    Assertions.assertTrue(app.containsBean("fileServer"), "bean app wasn't found.");
  }
}
