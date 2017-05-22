package com.mercurievv.messaginghomework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * Created with IntelliJ IDEA.
 * User: Victor Mercurievv
 * Date: 5/22/2017
 * Time: 10:03 PM
 * Contacts: email: mercurievvss@gmail.com Skype: 'grobokopytoff' or 'mercurievv'
 */
@SpringBootApplication
@ComponentScan("com.mercurievv.messaginghomework")
@EnableConfigurationProperties(ApplicationProperties.class)
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
