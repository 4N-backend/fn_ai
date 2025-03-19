package com.fn.ai.order.presentation;

import com.fn.ai.common.application.CommonResponse;
import com.fn.ai.common.context.UserContext;
import com.fn.ai.common.context.annotation.CurrentUserInfo;
import com.fn.ai.common.exception.code.CommonResponseCode;
import com.fn.ai.order.application.OrderService;
import com.fn.ai.order.presentation.dto.OrderCreateRequestDto;
import com.fn.ai.order.presentation.dto.OrderCreateResponseDto;
import com.fn.ai.order.presentation.dto.OrderResponseDto;
import com.fn.ai.order.presentation.dto.OrderUpdateRequestDto;
import com.fn.ai.order.presentation.dto.OrderUpdateResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  public ResponseEntity<CommonResponse<OrderCreateResponseDto>> createOrder(
      @RequestBody OrderCreateRequestDto requestDto) {
    return CommonResponse.of(CommonResponseCode.CREATED.getCode(),
        CommonResponseCode.CREATED.getMessage(), orderService.createOrder(requestDto));
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<CommonResponse<OrderResponseDto>> findByOrderId(
      @PathVariable UUID orderId) {
    return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(),
        CommonResponseCode.SUCCESS.getMessage(), orderService.findByOrderId(orderId));
  }

  @PutMapping("/{orderId}")
  public ResponseEntity<CommonResponse<OrderUpdateResponseDto>> updateOrder(
      @PathVariable UUID orderId,
      @RequestBody OrderUpdateRequestDto requestDto) {
    return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(),
        CommonResponseCode.SUCCESS.getMessage(), orderService.updateOrder(requestDto, orderId));
  }

  @DeleteMapping("/{orderId}")
  public ResponseEntity<CommonResponse<OrderResponseDto>> deleteOrder(
      @PathVariable UUID orderId,
      @CurrentUserInfo UserContext userInfo) {

    return CommonResponse.of(CommonResponseCode.NO_CONTENT.getCode(),
        CommonResponseCode.NO_CONTENT.getMessage(), orderService.deleteOrder(orderId, userInfo));
  }
}

