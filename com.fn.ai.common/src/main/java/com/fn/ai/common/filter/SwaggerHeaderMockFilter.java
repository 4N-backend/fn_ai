package com.fn.ai.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import org.springframework.web.filter.OncePerRequestFilter;

public class SwaggerHeaderMockFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String uri = request.getRequestURI();

        if (uri.equals("/signup") || uri.equals("signin")) {
            filterChain.doFilter(request,response);
            return;
        }

        if (uri.startsWith("/v3/api-docs") ||
            uri.startsWith("/swagger-ui") ||
            uri.startsWith("/swagger-resources") ||
            uri.startsWith("/webjars") ||
            uri.startsWith("/favicon.ico") ||
            uri.equals("/swagger-ui.html")) {
            if (request.getHeader("X-User-Id") == null) {
                MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(request);
                mutableRequest.putHeader("X-User-Id", UUID.randomUUID().toString());
                mutableRequest.putHeader("X-User-Name", "swagger");
                mutableRequest.putHeader("X-User-Role", "MASTER");

                filterChain.doFilter(mutableRequest, response);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
