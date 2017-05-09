package com.daggerok.spring.streaming.fileserver;

import com.daggerok.spring.streaming.fileserver.config.AppCfg;
import com.daggerok.spring.streaming.fileserver.config.Jsr310JpaCfg;
import com.daggerok.spring.streaming.fileserver.config.MustacheCfg;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({
        AppCfg.class,
        MustacheCfg.class,
        Jsr310JpaCfg.class
})
@SpringBootApplication
public class App {

    public static void main(String[] args) {

        SpringApplication
                .run(App.class, args)
                .registerShutdownHook();
    }
}
