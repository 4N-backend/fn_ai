package com.fn.ai.common.context;

import java.util.UUID;
import lombok.Builder;

@Builder
public record UserContext(
    UUID userId,
    String username,
    UserRoleEnum userRole) {

}
