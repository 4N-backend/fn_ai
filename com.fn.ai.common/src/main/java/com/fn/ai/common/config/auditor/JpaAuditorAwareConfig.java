package com.fn.ai.common.config.auditor;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "customAuditorAware")
@Slf4j
public class JpaAuditorAwareConfig {
    @Bean
    public AuditorAware<String> customAuditorAware() {
        return new CustomAuditorAware();
    }

    @PostConstruct
    public void init() {
        log.info("✅ JPA Auditing is enabled!");
    }
}
