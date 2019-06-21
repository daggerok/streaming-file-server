package daggerok.config;

import daggerok.config.props.AppProps;
import daggerok.config.props.DbProps;
import daggerok.config.props.FileItemsRestServiceProps;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    FileItemsRestServiceProps.class,
    AppProps.class,
    DbProps.class,
})
public class PropsAutoConfiguration { }
