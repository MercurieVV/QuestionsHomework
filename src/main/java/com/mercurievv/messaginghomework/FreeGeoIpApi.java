package com.mercurievv.messaginghomework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Victor Mercurievv
 * Date: 5/24/2017
 * Time: 11:33 PM
 * Contacts: email: mercurievvss@gmail.com Skype: 'grobokopytoff' or 'mercurievv'
 */
@Service
public class FreeGeoIpApi {
    private static Logger log = LoggerFactory.getLogger(FreeGeoIpApi.class);
    public String getCsv(String ip) {

        try {
            String url = "https://freegeoip.net/csv/" + ip;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");


            int responseCode = con.getResponseCode();
            if(responseCode >= 300)
                throw new Exception("Error response");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } catch (Exception e) {
            log.error("Error getting ip " + ip, e);
            return ",,,";
        }

    }

}
