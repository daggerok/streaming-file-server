package daggerok.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AppProps {

  public Config upload;
  public Config download;
  public Config github;

  @Data
  public static class Config {
    public String path, url;
  }
}
