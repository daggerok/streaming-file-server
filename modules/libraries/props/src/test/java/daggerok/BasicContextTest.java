package daggerok;

import daggerok.config.PropsAutoConfiguration;
import daggerok.config.props.DbProps;
import daggerok.config.props.FileItemsRestServiceProps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = NONE,
    classes = PropsAutoConfiguration.class)
public class BasicContextTest {

  @Autowired
  ConfigurableApplicationContext app;

  @Test
  public void testContext() throws Exception {
    assertNotNull("bean dbProps wasn't found.", app.getBean(DbProps.class));
    assertNotNull("bean appProps wasn't found.", app.getBean(DbProps.class));
    assertNotNull("bean fileItemsRestServiceProps wasn't found.", app.getBean(FileItemsRestServiceProps.class));
  }
}
