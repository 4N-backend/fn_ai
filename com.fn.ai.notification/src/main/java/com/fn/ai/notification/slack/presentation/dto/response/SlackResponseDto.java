package com.fn.ai.notification.slack.presentation.dto.response;

import com.fn.ai.notification.slack.domain.model.Slack;
import java.sql.Timestamp;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SlackResponseDto {
    private UUID slackId;
    private String message;
    private String recipientSlackId;
    private Timestamp sentAt;

    @Builder
    private SlackResponseDto(UUID slackId, String message, String recipientSlackId, Timestamp sentAt) {
        this.slackId = slackId;
        this.message = message;
        this.recipientSlackId = recipientSlackId;
        this.sentAt = sentAt;
    }

    public static SlackResponseDto from(Slack slack) {
        return SlackResponseDto.builder()
                .slackId(slack.getSlackId())
                .message(slack.getMessage())
                .recipientSlackId(slack.getRecipientSlackId())
                .sentAt(slack.getSentAt())
                .build();
    }




}
