package com.fn.ai.common.application;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class CommonResponse<T> {

    private final HttpStatus status;
    private final String message;
    private final T result;

    /**
     * 성공 메서드
     */
    public static <T> CommonResponse<T> success(T result) {
        return new CommonResponse<>(HttpStatus.OK, "success", result);
    }

    public static <T> CommonResponse<T> success(String message, T result) {
        return new CommonResponse<>(HttpStatus.OK, message, result);
    }
    /**
     * 에러
     */
    public static <T> CommonResponse<T> error(HttpStatus status, String message) {
        return new CommonResponse<>(status, message, null);
    }

    public static <T> CommonResponse<T> badRequest(String message) {
        return new CommonResponse<>(HttpStatus.BAD_REQUEST, message, null);
    }

    public static <T> CommonResponse<T> notFound(String message) {
        return new CommonResponse<>(HttpStatus.NOT_FOUND, message, null);
    }

    public static <T> CommonResponse<T> unauthorized(String message) {
        return new CommonResponse<>(HttpStatus.UNAUTHORIZED, message, null);
    }


    /**
     * of

     */
    public static <T> CommonResponse<T> of(HttpStatus status, String message,
        T result) {
        return new CommonResponse<>(status, message, result);
    }

    public boolean isSuccess(){
        return this.status.is2xxSuccessful();
    }

}
