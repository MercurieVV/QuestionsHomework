package com.mercurievv.messaginghomework.db;

import com.mercurievv.messaginghomework.ApplicationProperties;
import com.querydsl.sql.H2Templates;
import com.querydsl.sql.SQLQueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Provider;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Created with IntelliJ IDEA.
 * User: Victor Mercurievv
 * Date: 5/28/2017
 * Time: 11:05 PM
 * Contacts: email: mercurievvss@gmail.com Skype: 'grobokopytoff' or 'mercurievv'
 */
@Service
public class DbHelper {
    private final ApplicationProperties applicationProperties;
    private static Logger log = LoggerFactory.getLogger(DbHelper.class);

    @Autowired
    public DbHelper(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
        try {
            Class.forName("org.h2.Driver").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            log.error("Initing driver", e);
        }
    }

    public SQLQueryFactory getQueryFactory() {
        ApplicationProperties.Db dbProps = applicationProperties.getDb();
        return new SQLQueryFactory(H2Templates.DEFAULT, () -> {
            try {
                return DriverManager.getConnection(
                        dbProps.getUrl()
                        , dbProps.getUser()
                        , dbProps.getPassword());
            } catch (SQLException e) {
                log.error("Error creatin db connection", e);
                throw new RuntimeException(e);
            }
        });
    }
}
