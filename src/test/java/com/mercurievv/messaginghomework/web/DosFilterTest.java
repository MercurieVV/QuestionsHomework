package com.mercurievv.messaginghomework.web;

import com.mercurievv.messaginghomework.external.FreeGeoIpApi;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: Victor Mercurievv
 * Date: 5/24/2017
 * Time: 11:56 PM
 * Contacts: email: mercurievvss@gmail.com Skype: 'grobokopytoff' or 'mercurievv'
 */
class DosFilterTest {
    private final FreeGeoIpApi freeGeoIpApiMock = mock(FreeGeoIpApi.class);
    private DosFilter dosFilter = new DosFilter(freeGeoIpApiMock);

    @Test
    void getCountryCodeByIp_found() {
        when(freeGeoIpApiMock.getCsv(any())).thenReturn("128.128.128.128,US,United States,CA,California,Mountain View,94035,America/Los_Angeles,37.3860,-122.0838,807");

        assertThat(dosFilter.getCountryCodeByIp("")).isEqualTo("US");
    }

    @Test
    void getCountryCodeByIp_notFound() {
        when(freeGeoIpApiMock.getCsv(any())).thenReturn("127.0.0.1,,,,,,,,0.0000,0.0000,0");

        assertThat(dosFilter.getCountryCodeByIp("")).isEqualTo("LV");
    }

    @Test
    void cache() {
        reset(freeGeoIpApiMock);
        when(freeGeoIpApiMock.getCsv(any())).thenReturn("128.128.128.128,US,United States,CA,California,Mountain View,94035,America/Los_Angeles,37.3860,-122.0838,807");

        verify(freeGeoIpApiMock, times(0)).getCsv(any());

        assertThat(dosFilter.getCountryCodeByIpCached("")).isEqualTo("US");
        verify(freeGeoIpApiMock, times(1)).getCsv(any());

        assertThat(dosFilter.getCountryCodeByIpCached("")).isEqualTo("US");
        verify(freeGeoIpApiMock, times(1)).getCsv(any());
    }
}