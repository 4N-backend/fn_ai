package com.fn.ai.delivery.exception;

import com.fn.ai.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class DeliverySequenceOutOfRangeException extends BaseException {

  private static final String MESSAGE = "배송 순번 범위가 유효하지 않습니다.";

  public DeliverySequenceOutOfRangeException() {
    super(HttpStatus.BAD_REQUEST, MESSAGE);
  }
}
