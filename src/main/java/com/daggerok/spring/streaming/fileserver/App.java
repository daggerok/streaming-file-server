package com.daggerok.spring.streaming.fileserver;

import com.daggerok.spring.streaming.fileserver.config.AppCfg;
import com.daggerok.spring.streaming.fileserver.config.AuditConfig;
import com.daggerok.spring.streaming.fileserver.config.Jsr310JpaCfg;
import com.daggerok.spring.streaming.fileserver.config.MustacheCfg;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.util.TimeZone;

@Import({
    AppCfg.class,
    AuditConfig.class,
    MustacheCfg.class,
    Jsr310JpaCfg.class,
})
@SpringBootApplication
public class App {

  public static void main(final String[] args) {

    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

    SpringApplication
        .run(App.class, args)
        .registerShutdownHook();
  }
}
