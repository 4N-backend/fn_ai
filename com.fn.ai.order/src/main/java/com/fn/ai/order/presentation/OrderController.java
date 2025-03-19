package com.fn.ai.order.presentation;

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
  public ResponseEntity<OrderCreateResponseDto> createOrder(
      @RequestBody OrderCreateRequestDto requestDto) {
    return ResponseEntity.ok().body(orderService.createOrder(requestDto));
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<OrderResponseDto> findByOrderId(
      @PathVariable UUID orderId) {
    return ResponseEntity.ok().body(orderService.findByOrderId(orderId));
  }

  @PutMapping("/{orderId}")
  public ResponseEntity<OrderUpdateResponseDto> updateOrder(
      @PathVariable UUID orderId,
      @RequestBody OrderUpdateRequestDto requestDto) {
    return ResponseEntity.ok().body(orderService.updateOrder(requestDto, orderId));
  }

  @DeleteMapping("/{orderId}")
  public ResponseEntity<Void> deleteOrder(
      @PathVariable UUID orderId) {
    orderService.deleteOrder(orderId);
    return ResponseEntity.ok().build();
  }
}

