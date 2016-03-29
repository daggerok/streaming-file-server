package com.daggerok.spring.streaming.fileserver.config.spring;

import com.daggerok.spring.streaming.fileserver.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertTrue;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
public class BasicContextTest {
    @Autowired
    ConfigurableApplicationContext app;

    @Test
    public void testContext() throws Exception {
        assertTrue("bean app wasn't found.", app.containsBean("app"));
    }
}
