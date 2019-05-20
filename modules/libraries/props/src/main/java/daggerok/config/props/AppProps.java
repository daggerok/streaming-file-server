package daggerok.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AppProps {

  private Config upload, download, github;

  @Data
  public static class Config {
    private String path, url;
  }
}
