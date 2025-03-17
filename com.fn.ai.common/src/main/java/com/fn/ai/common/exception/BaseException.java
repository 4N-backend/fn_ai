package com.fn.ai.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class BaseException extends RuntimeException {

    private final HttpStatus status;

    public BaseException(HttpStatus status, String message){
        super(message);
        this.status = status;
    }
}
