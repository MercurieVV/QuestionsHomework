package com.mercurievv.messaginghomework.web;

import org.eclipse.jetty.servlets.DoSFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import java.util.concurrent.ConcurrentMap;

/**
 * Created with IntelliJ IDEA.
 * User: Victor Mercurievv
 * Date: 5/22/2017
 * Time: 10:15 PM
 * Contacts: email: mercurievvss@gmail.com Skype: 'grobokopytoff' or 'mercurievv'
 */
@Component
public class DosFilter extends DoSFilter {
    private final CountryCodeResolver countryCodeResolver;

    @Autowired
    public DosFilter(CountryCodeResolver countryCodeResolver) {
        this.countryCodeResolver = countryCodeResolver;
    }

    @Override
    public int getMaxRequestsPerSec() {
        return 5;
    }

    @Override
    public long getDelayMs() {
        return -1;
    }

    @Override
    protected String extractUserId(ServletRequest request) {
        String clientIp = request.getRemoteAddr();
        return countryCodeResolver.getCountryCodeByIpCached(clientIp);
    }

}
