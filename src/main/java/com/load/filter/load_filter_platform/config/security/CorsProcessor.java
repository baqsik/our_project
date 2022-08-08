package com.load.filter.load_filter_platform.config.security;

import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.DefaultCorsProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class CorsProcessor extends DefaultCorsProcessor {
    private final ArrayList<String> origins = new ArrayList<String>();

    public void addOrigin(String origin) {
        origins.add(origin);
    }

    @Override
    public boolean processRequest(CorsConfiguration config, HttpServletRequest request, HttpServletResponse response) throws IOException {
        for (String origin : origins) {
            config.addAllowedOrigin(origin);
        }
        origins.clear();
        return super.processRequest(config, request, response);
    }
}
