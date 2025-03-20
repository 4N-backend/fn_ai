package com.fn.ai.user.presentation.dto;

import com.fn.ai.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fn.ai.common.context.UserRoleEnum;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponseDto {

    private UUID id;
    private String username;
    private UserRoleEnum role;
    private String slackId;



    public static UserInfoResponseDto of(User user) {
        return new UserInfoResponseDto(
                user.getId(),
                user.getUsername().getValue(),
                user.getRole(),
                user.getSlackId().getValue()
        );
    }
}
