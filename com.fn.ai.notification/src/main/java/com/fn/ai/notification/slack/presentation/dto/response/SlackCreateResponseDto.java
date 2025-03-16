package com.fn.ai.notification.slack.presentation.dto.response;

import com.fn.ai.notification.slack.domain.model.Slack;
import lombok.*;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class SlackCreateResponseDto {
    private UUID slackId;
    private String message;
    private String recipientSlackId;
    private Timestamp sentAt;

    @Builder
    private SlackCreateResponseDto(UUID slackId, String message, String recipientSlackId, Timestamp sentAt) {
        this.slackId = slackId;
        this.message = message;
        this.recipientSlackId = recipientSlackId;
        this.sentAt = sentAt;
    }

    public static SlackCreateResponseDto from(Slack slack) {
        return SlackCreateResponseDto.builder()
                .slackId(slack.getSlackId())
                .message(slack.getMessage())
                .recipientSlackId(slack.getRecipientSlackId())
                .sentAt(slack.getSentAt())
                .build();
    }




}
