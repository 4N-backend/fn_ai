package com.fn.ai.delivery.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.fn.ai.common.exception.BaseException;

public class AlreadyCompletedDelivery extends BaseException {

  public static final String MESSAGE = "이미 완료된 배송입니다.";

  public AlreadyCompletedDelivery() {
    super(BAD_REQUEST, MESSAGE);
  }
}
