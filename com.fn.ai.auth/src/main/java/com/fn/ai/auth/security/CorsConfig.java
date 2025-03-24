package com.fn.ai.auth.security;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    servers = @Server(url = "http://localhost:18081")
)
public class CorsConfig {

}
