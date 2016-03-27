package com.daggerok.spring.streaming.fileserver.web.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@Documented
@Retention(RUNTIME)
@Target({METHOD, TYPE})
@RequestMapping(method = GET)
public @interface Get {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] value() default {};

    @AliasFor(annotation = RequestMapping.class, attribute = "value")
    String[] path() default {};

    @AliasFor(annotation = RequestMapping.class, attribute = "produces")
    String[] produces() default {};
}
