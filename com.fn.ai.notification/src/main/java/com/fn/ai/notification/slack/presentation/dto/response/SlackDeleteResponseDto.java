package com.fn.ai.notification.slack.presentation.dto.response;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SlackDeleteResponseDto {
    private final UUID slackId;

    @Builder
    public SlackDeleteResponseDto(UUID slackId) {
        this.slackId = slackId;
    }
}
