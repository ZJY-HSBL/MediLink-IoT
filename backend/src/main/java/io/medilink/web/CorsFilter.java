package io.medilink.web;

import io.medilink.config.AppConfig;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/api/*")
public class CorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse http = (HttpServletResponse) response;
        http.setHeader("Access-Control-Allow-Origin", AppConfig.get("cors.allowedOrigin", "*"));
        http.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        http.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        chain.doFilter(request, response);
    }
}
