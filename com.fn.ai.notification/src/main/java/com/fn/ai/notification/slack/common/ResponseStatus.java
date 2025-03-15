package com.fn.ai.notification.slack.common;

import lombok.Getter;

@Getter
public enum ResponseStatus {
    CREATE_SLACK_SUCCESS("SLACK011", "Slack 메시지 생성에 성공하였습니다.");

    private final String code;
    private final String message;

    ResponseStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
