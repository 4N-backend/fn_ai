package com.fn.ai.notification.slack.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseDataDto<?>> handleCustomException(CustomException ex) {
        ErrorType errorType = ex.getErrorType();
        ResponseDataDto<?> response = new ResponseDataDto<>(errorType.getCode(), errorType.getMessage(), null);
        return new ResponseEntity<>(response, errorType.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDataDto<?>> handleGeneralException(Exception ex) {
        ResponseDataDto<?> response = new ResponseDataDto<>("SRV500", "서버 내부 오류가 발생하였습니다.", null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
