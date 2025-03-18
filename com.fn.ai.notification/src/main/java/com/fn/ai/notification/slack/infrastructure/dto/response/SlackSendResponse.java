package com.fn.ai.notification.slack.infrastructure.dto.response;

import java.sql.Timestamp;
import lombok.Getter;

@Getter
public class SlackSendResponse {
    private final Timestamp sentAt;
    private final String ts;
    private final String channel;  // 실제 채널 ID 추가

    public SlackSendResponse(Timestamp sentAt, String ts, String channel) {
        this.sentAt = sentAt;
        this.ts = ts;
        this.channel = channel;
    }
}
