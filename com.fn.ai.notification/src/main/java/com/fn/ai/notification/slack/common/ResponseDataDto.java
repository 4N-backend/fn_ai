package com.fn.ai.notification.slack.common;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@Getter
@JsonPropertyOrder({"code", "message", "results"})
public class ResponseDataDto<T> {
    private final String code;
    private final String message;
    private final T results;

    public ResponseDataDto(String code, String message, T results) {
        this.code = code;
        this.message = message;
        this.results = results;
    }

    // 성공 응답용
    public ResponseDataDto(ResponseStatus status, T results) {
        this(status.getCode(), status.getMessage(), results);
    }
}
