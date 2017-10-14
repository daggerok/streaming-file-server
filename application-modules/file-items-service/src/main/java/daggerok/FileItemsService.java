package daggerok;

import daggerok.config.AuditConfig;
import daggerok.config.Jsr310JpaCfg;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.util.TimeZone;

@Import({
    AuditConfig.class,
    Jsr310JpaCfg.class,
})
@SpringBootApplication
public class FileItemsService {

  public static void main(final String[] args) {

    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

    SpringApplication
        .run(FileItemsService.class, args)
        .registerShutdownHook();
  }
}
