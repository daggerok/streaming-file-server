package daggerok.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "db")
public class DbProps {

  private String name;
  private String user;
  private String pass;
  private Server server;

  @Data
  public static class Server {
    public String host;
    public Integer port;
  }
}
