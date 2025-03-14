package com.fn.ai.gateway.filter;

import com.fn.ai.gateway.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;
@Component
@RequiredArgsConstructor
public class CustomPreFilter implements GlobalFilter, Ordered {

    private static final Logger logger = Logger.getLogger(CustomPreFilter.class.getName());

    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        logger.info("Pre Filter : request URI: "+ request.getURI());

        if (path.equals("/api/auth/signup") || path.equals("/api/auth/signin")
                || path.contains("/v3/api-docs")) {
            return chain.filter(exchange);
        }

        String tokenValue = jwtUtil.getJwtFromHeader(request);

        if (StringUtils.hasText(tokenValue)) {

            if (!jwtUtil.validateToken(tokenValue)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            Claims info = jwtUtil.getUserInfoFromToken(tokenValue);

            exchange.getRequest().mutate()
                    .header("X-User-Id", (String) info.get("id"))
                    .header("X-User-Name", (String) info.get("username"))
                    .header("X-User-Role", (String) info.get("role"))
                    .build();
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
