package daggerok;

import daggerok.config.AppCfg;
import daggerok.web.config.MustacheCfg;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.util.TimeZone;

@Import({
    AppCfg.class,
    MustacheCfg.class,
})
@SpringBootApplication
public class FileServer {

  public static void main(final String[] args) {

    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

    SpringApplication
        .run(FileServer.class, args)
        .registerShutdownHook();
  }
}
