package com.fn.ai.delivery.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.fn.ai.common.exception.BaseException;

public class DeliveryNotFoundException extends BaseException {

  private static final String MESSAGE = "존재하지 않는 배송입니다.";

  public DeliveryNotFoundException() {
    super(NOT_FOUND, MESSAGE);
  }
}
