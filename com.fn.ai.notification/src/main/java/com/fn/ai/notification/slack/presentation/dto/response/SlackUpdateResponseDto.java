package com.fn.ai.notification.slack.presentation.dto.response;

import com.fn.ai.notification.slack.domain.model.Slack;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SlackUpdateResponseDto {
    private UUID slackId;
    private String message;

    @Builder
    private SlackUpdateResponseDto(UUID slackId, String message) {
        this.slackId = slackId;
        this.message = message;
    }

    public static SlackUpdateResponseDto from(Slack slack) {
        return SlackUpdateResponseDto.builder()
                .slackId(slack.getSlackId())
                .message(slack.getMessage())
                .build();
    }
}
