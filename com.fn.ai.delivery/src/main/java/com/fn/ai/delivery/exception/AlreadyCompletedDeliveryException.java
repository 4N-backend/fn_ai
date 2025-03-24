package com.fn.ai.delivery.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.fn.ai.common.exception.BaseException;

public class AlreadyCompletedDeliveryException extends BaseException {

  public static final String MESSAGE = "이미 완료된 배송입니다.";

  public AlreadyCompletedDeliveryException() {
    super(BAD_REQUEST, MESSAGE);
  }
}
