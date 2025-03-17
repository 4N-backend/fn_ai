package com.fn.ai.common.exception;

import com.fn.ai.common.application.CommonResponse;
import com.fn.ai.common.exception.code.CommonResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<CommonResponse<Void>> handleGenericException(Exception e){
    return CommonResponse.of(CommonResponseCode.SERVER_ERROR.getCode(),
        CommonResponseCode.SERVER_ERROR.getMessage(),null );
  }

  @ExceptionHandler(BaseException.class)
    public ResponseEntity<CommonResponse<Void>> handleBaseException(BaseException e){
    return CommonResponse.of(e.getStatus(), e.getMessage(),null);
  }

}
