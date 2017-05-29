package com.mercurievv.messaginghomework.web;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.mercurievv.messaginghomework.external.FreeGeoIpApi;
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
    private final FreeGeoIpApi freeGeoIpApi;
    ConcurrentMap<String, String> cache = new ConcurrentLinkedHashMap.Builder<String, String>()
            .maximumWeightedCapacity(1000)
            .build();

    @Autowired
    public DosFilter(FreeGeoIpApi freeGeoIpApi) {
        this.freeGeoIpApi = freeGeoIpApi;
        setDelayMs(-1);
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
        return getCountryCodeByIpCached(clientIp);
    }

    String getCountryCodeByIpCached(String clientIp) {
        return cache.computeIfAbsent(clientIp, s -> getCountryCodeByIp(clientIp));
    }

    String getCountryCodeByIp(String clientIp) {
        String csv = freeGeoIpApi.getCsv(clientIp);
        String countryCode = csv.split(",", 3)[1];
        return "".equals(countryCode) ? "LV" : countryCode;
    }

}
