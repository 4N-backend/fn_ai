package com.fn.ai.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<?> handleBaseException(BaseException e){
    return ResponseEntity.status(e.getStatus()).body(e.getMessage());
  }
}
