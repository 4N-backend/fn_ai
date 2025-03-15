package com.fn.ai.notification.slack.common;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private String result;
    private ErrorType errorType;

    public CustomException(ErrorType errorType) {
        this.result = "ERROR";
        this.errorType = errorType;
    }
}
