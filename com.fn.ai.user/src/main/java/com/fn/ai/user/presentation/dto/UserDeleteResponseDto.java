package com.fn.ai.user.presentation.dto;

import com.fn.ai.user.model.User;
import java.util.UUID;

public record UserDeleteResponseDto (
        UUID userId,
        String username
) {
    public static UserDeleteResponseDto of(User user) {
        return new UserDeleteResponseDto(user.getId(), user.getUsername().getValue());
    }
}
