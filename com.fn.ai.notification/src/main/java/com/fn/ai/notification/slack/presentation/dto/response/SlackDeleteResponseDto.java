package com.fn.ai.notification.slack.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class SlackDeleteResponseDto {
    private final UUID slackId;

    @Builder
    public SlackDeleteResponseDto(UUID slackId) {
        this.slackId = slackId;
    }
}
