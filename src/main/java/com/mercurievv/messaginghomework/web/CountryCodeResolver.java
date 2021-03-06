package com.mercurievv.messaginghomework.web;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.mercurievv.messaginghomework.external.FreeGeoIpApi;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentMap;

/**
 * Created with IntelliJ IDEA.
 * User: Victor Mercurievv
 * Date: 5/29/2017
 * Time: 10:31 AM
 * Contacts: email: mercurievvss@gmail.com Skype: 'grobokopytoff' or 'mercurievv'
 */
@Service
public class CountryCodeResolver {
    private final FreeGeoIpApi freeGeoIpApi;
    private Cache<String, String> cache = Caffeine.newBuilder()
            .maximumSize(1000)
            .build();

    public CountryCodeResolver(FreeGeoIpApi freeGeoIpApi) {
        this.freeGeoIpApi = freeGeoIpApi;
    }

    public String getCountryCodeByIpCached(String clientIp) {
        return cache.get(clientIp, s -> getCountryCodeByIp(clientIp));
    }

    String getCountryCodeByIp(String clientIp) {
        String csv = freeGeoIpApi.getCsv(clientIp);
        String countryCode = csv.split(",", 3)[1];
        return "".equals(countryCode) ? "LV" : countryCode;
    }

}
