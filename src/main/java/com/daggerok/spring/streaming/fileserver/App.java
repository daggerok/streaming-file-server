package com.daggerok.spring.streaming.fileserver;

import com.daggerok.spring.streaming.fileserver.config.H2ConsoleCfg;
import com.daggerok.spring.streaming.fileserver.config.Jsr310JpaCfg;
import com.daggerok.spring.streaming.fileserver.config.MustacheCfg;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({
        MustacheCfg.class,
        H2ConsoleCfg.class,
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
