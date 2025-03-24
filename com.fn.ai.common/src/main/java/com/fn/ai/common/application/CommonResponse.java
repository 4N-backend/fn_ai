package com.fn.ai.common.application;

import com.fn.ai.common.exception.BaseException;
import com.fn.ai.common.exception.code.CommonResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
@Getter
public class CommonResponse<T> {

  private final String message;
  private final T result;

  /**
   * 성공 메서드
   */
  public static <T> ResponseEntity<CommonResponse<T>> success(T result) {
    return new ResponseEntity<>(
        createCommonResponse(CommonResponseCode.SUCCESS.getMessage(), result),
        HttpStatus.OK);
  }

  public static <T> ResponseEntity<CommonResponse<T>> success(String message, T result) {
    return new ResponseEntity<>(createCommonResponse(message, result), HttpStatus.OK);
  }

  public static ResponseEntity<CommonResponse<Void>> error(BaseException e) {
    return new ResponseEntity<>(createCommonResponse(e.getMessage(), null), e.getStatus());
  }

  /**
   * 에러
   */
  public static <T> ResponseEntity<CommonResponse<T>> badRequest() {
    return new ResponseEntity<>(
        createCommonResponse(CommonResponseCode.BAD_REQUEST.getMessage(), null),
        HttpStatus.BAD_REQUEST);
  }

  public static <T> ResponseEntity<CommonResponse<T>> notFound() {
    return new ResponseEntity<>(
        createCommonResponse(CommonResponseCode.DATA_NOT_FOUND.getMessage(), null),
        HttpStatus.NOT_FOUND);
  }

  public static <T> ResponseEntity<CommonResponse<T>> unauthorized() {
    return new ResponseEntity<>(
        createCommonResponse(CommonResponseCode.UNAUTHORIZED.getMessage(), null),
        HttpStatus.UNAUTHORIZED);

  }

  /**
   * of status : HttpStatus message result
   */
  public static <T> ResponseEntity<CommonResponse<T>> of(HttpStatus status, String message,
      T result) {
    return new ResponseEntity<>(createCommonResponse(message, result), status);
  }

  private static <T> CommonResponse<T> createCommonResponse(String message,
      T result) {
    return new CommonResponse<>(message, result);
  }
}
