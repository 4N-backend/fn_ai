package com.fn.ai.notification.slack.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
    // Slack 메시지 전송 실패
    SLACK_SEND_FAILED(HttpStatus.BAD_REQUEST, "SLACK001", "Slack 메시지 전송에 실패하였습니다."),
    // 잘못된 수신자 정보가 전달된 경우
    INVALID_RECIPIENT(HttpStatus.BAD_REQUEST, "SLACK002", "유효하지 않은 수신자 정보입니다."),
    // 지정한 채널을 찾을 수 없는 경우
    CHANNEL_NOT_FOUND(HttpStatus.NOT_FOUND, "SLACK003", "지정한 Slack 채널을 찾을 수 없습니다."),
    // 지정한 사용자를 찾을 수 없는 경우
    SLACK_NOT_FOUND(HttpStatus.NOT_FOUND, "SLACK004", "지정한 Slack 사용자를 찾을 수 없습니다."),
    // Slack API 내부 오류 발생 시
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SLACK500", "Slack 처리 중 내부 서버 오류가 발생하였습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
