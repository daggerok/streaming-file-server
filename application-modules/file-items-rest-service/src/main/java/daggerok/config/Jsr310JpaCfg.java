package daggerok.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@Configuration
@ComponentScan(basePackageClasses = Jsr310JpaConverters.class)
public class Jsr310JpaCfg {}
