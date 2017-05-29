package com.mercurievv.messaginghomework;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Victor Mercurievv
 * Date: 5/22/2017
 * Time: 10:05 PM
 * Contacts: email: mercurievvss@gmail.com Skype: 'grobokopytoff' or 'mercurievv'
 */
@Component
@ConfigurationProperties(prefix="app")
@Getter
@Setter
@NoArgsConstructor(force = true)
//@AllArgsConstructor
public class ApplicationProperties {
    private Db db;

    @Getter
    @Setter
    @NoArgsConstructor(force = true)
    public static class Db {
        private String url;
        private String user;
        private String password;
    }
}
