package com.fn.ai.notification.slack.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SlackCreateRequestDto {
    private String recipientSlackId;
    private String message;
}
