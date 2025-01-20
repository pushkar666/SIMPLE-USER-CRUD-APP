package com.user_management.user_management_spring_boot.filter;

import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A filter component that handles Cross-Origin Resource Sharing (CORS) requests.
 * This filter is responsible for adding necessary headers to HTTP responses to allow
 * requests from a specific origin (http://localhost:3000) and to handle preflight requests.
 */
@Component
public class CorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialize the filter. This method is called by the web container when the filter is first created.
    }

    @Override
    public void destroy() {
        // Perform any cleanup operations when the filter is destroyed.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Set the necessary CORS headers in the HTTP response.
        httpResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, X-Requested-With");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");

        // Handle preflight requests (OPTIONS method) by setting the response status to OK.
        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            httpResponse.setStatus(HttpServletResponse.SC_OK);
        } else {
            // For non-preflight requests, continue the filter chain.
            chain.doFilter(request, response);
        }
    }

}
