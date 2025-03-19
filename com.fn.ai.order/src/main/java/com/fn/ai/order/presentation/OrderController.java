package com.fn.ai.order.presentation;

import com.fn.ai.order.application.service.OrderService;
import com.fn.ai.order.presentation.dto.OrderCreateRequestDto;
import com.fn.ai.order.presentation.dto.OrderCreateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  public ResponseEntity<OrderCreateResponseDto> createOrder(
      @RequestBody OrderCreateRequestDto requestDto) {
    return ResponseEntity.ok().body(orderService.createOrder(requestDto));
  }
}
