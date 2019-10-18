package com.sgaraba.springmastering.utils;

import org.springframework.http.HttpHeaders;

import java.nio.charset.Charset;
import java.util.Base64;

public class TestUtils {

    public static final String LOCAL_HOST = "http://localhost:";

    public static HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {
            {
                String auth = username + ":" + password;
                byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("US-ASCII")));
                String authHeader = "Basic " + new String(encodedAuth);
                set("Authorization", authHeader);
            }
        };
    }

    public static String createURL(String uri, int port) {
        return String.format("%s%s%s", LOCAL_HOST, port, uri);
    }
}
