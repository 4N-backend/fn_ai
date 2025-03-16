package com.fn.ai.notification.slack.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "p_slack_message")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Slack {

    @Id
    @Column(name = "slack_id", nullable = false, unique = true)
    private UUID slackId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(name = "recipient_slack_id", nullable = false)
    private String recipientSlackId;

    // Slack 채널 ID
    @Column(name = "channel_id", nullable = false)
    private String channelId;

    @Column(name = "sent_at", nullable = false)
    private Timestamp sentAt;

    // 원래 Slack API의 ts 문자열 저장
    @Column(name = "slack_ts", nullable = false)
    private String slackTs;

    @Builder
    private Slack(UUID slackId, UUID userId, String message, String recipientSlackId, String channelId, Timestamp sentAt, String slackTs) {
        this.slackId = slackId;
        this.userId = userId;
        this.message = message;
        this.recipientSlackId = recipientSlackId;
        this.channelId = channelId;
        this.sentAt = sentAt;
        this.slackTs = slackTs;
    }

    /**
     * @param userId  사용자 ID
     * @param message 전달할 메시지
     * @param recipientSlackId 수신자 슬랙 ID
     * @param sentAt  발송 시간
     * @return 생성된 Slack 객체
     */
    public static Slack of(UUID userId, String message, String recipientSlackId, String channelId, Timestamp sentAt, String slackTs) {
        return Slack.builder()
                .slackId(UUID.randomUUID())
                .userId(userId)
                .message(message)
                .recipientSlackId(recipientSlackId)
                .channelId(channelId)
                .sentAt(sentAt)
                .slackTs(slackTs)
                .build();
    }
}