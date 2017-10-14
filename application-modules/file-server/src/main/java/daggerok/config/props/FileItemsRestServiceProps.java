package daggerok.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "file-items-rest-service")
public class FileItemsRestServiceProps {

  private static final long serialVersionUID = 5365578966298764816L;

  String url;
}
