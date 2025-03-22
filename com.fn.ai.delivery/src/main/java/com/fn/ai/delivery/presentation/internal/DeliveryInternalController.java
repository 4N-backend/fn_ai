package com.fn.ai.delivery.presentation.internal;

import static com.fn.ai.common.exception.code.CommonResponseCode.CREATED;

import com.fn.ai.common.application.CommonResponse;
import com.fn.ai.delivery.application.DeliveryService;
import com.fn.ai.delivery.presentation.internal.dto.DeliveryCreateRequestDto;
import com.fn.ai.delivery.presentation.internal.dto.DeliveryCreateResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class DeliveryInternalController {

  private final DeliveryService deliveryService;

  /**
   * 배송 생성
   */
  @PostMapping
  public DeliveryCreateResponseDto create(
      @Valid @RequestBody DeliveryCreateRequestDto requestDto
  ) {
    return deliveryService.create(requestDto);
  }
}
