package daggerok.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasicContextTest {

  @Autowired
  ConfigurableApplicationContext app;

  @Test
  public void testContext() throws Exception {
    assertTrue("bean app wasn't found.", app.containsBean("fileServer"));
  }
}
