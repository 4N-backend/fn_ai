package com.fn.ai.common.config.auditor;

import com.fn.ai.common.context.UserContextHolder;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;

@Slf4j
public class CustomAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        String username = UserContextHolder.getUsername();
        log.info("🔍 AuditorAware: Current User = {}", username); // ✅ 추가 확인
        return Optional.ofNullable(UserContextHolder.getUsername());
    }
}
