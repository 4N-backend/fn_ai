package com.fn.ai.common.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CommonResponseCode {
    SUCCESS(HttpStatus.OK,"요청 성공"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    UNAUTHORIZED( HttpStatus.UNAUTHORIZED,"유효한 인증 정보가 아닙니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN,"권한이 없습니다."),
    DATA_NOT_FOUND(HttpStatus.NOT_FOUND,"데이터를 찾을 수 없습니다."),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"서버 에러");

    private final HttpStatus code;
    private final String message;


}
