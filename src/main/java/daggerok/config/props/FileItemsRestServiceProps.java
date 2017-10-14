package daggerok.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
@ConfigurationProperties(prefix = "file-items-rest-service")
public class FileItemsRestServiceProps implements Serializable {

  private static final long serialVersionUID = 5365578966298764816L;

  String url;
}
