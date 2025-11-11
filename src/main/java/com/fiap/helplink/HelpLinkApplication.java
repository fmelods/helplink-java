package com.fiap.helplink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HelpLinkApplication {
    public static void main(String[] args) {
        SpringApplication.run(HelpLinkApplication.class, args);
    }
}
