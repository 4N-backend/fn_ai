package com.fn.ai.delivery.presentation;

import com.fn.ai.delivery.application.DeliveryService;
import com.fn.ai.delivery.presentation.dto.DeliveryCreateRequestDto;
import com.fn.ai.delivery.presentation.dto.DeliveryCreateResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/deliveries")
public class DeliveryController {

  private final DeliveryService deliveryService;

  @PostMapping
  public ResponseEntity<?> create(@Valid @RequestBody DeliveryCreateRequestDto requestDto) {
    DeliveryCreateResponseDto responseDto = deliveryService.create(requestDto);
    return ResponseEntity.created(UriComponentsBuilder.fromUriString("/api/deliveries/{deliveryId}")
            .buildAndExpand(responseDto.deliveryId()).toUri())
        .body(responseDto);
  }
}
