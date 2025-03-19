package com.fn.ai.delivery.presentation;

import com.fn.ai.delivery.application.DeliveryService;
import com.fn.ai.delivery.presentation.dto.DeliveryCreateRequestDto;
import com.fn.ai.delivery.presentation.dto.DeliveryCreateResponseDto;
import com.fn.ai.delivery.presentation.dto.DeliveryDetailsResponseDto;
import com.fn.ai.delivery.presentation.dto.DeliverySummaryResponseDto;
import com.fn.ai.delivery.presentation.dto.DeliveryUpdateRequestDto;
import com.fn.ai.delivery.presentation.dto.DeliveryUpdateResponseDto;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/deliveries")
public class DeliveryController {

  private final DeliveryService deliveryService;

  @PostMapping
  public ResponseEntity<DeliveryCreateResponseDto> create(
      @Valid @RequestBody DeliveryCreateRequestDto requestDto
  ) {
    DeliveryCreateResponseDto responseDto = deliveryService.create(requestDto);
    return ResponseEntity.created(UriComponentsBuilder.fromUriString("/api/deliveries/{deliveryId}")
            .buildAndExpand(responseDto.deliveryId()).toUri())
        .body(responseDto);
  }

  @GetMapping("/{deliveryId}")
  public ResponseEntity<DeliveryDetailsResponseDto> readOne(@PathVariable UUID deliveryId) {
    return ResponseEntity.ok(deliveryService.readOne(deliveryId));
  }

  @GetMapping
  public ResponseEntity<List<DeliverySummaryResponseDto>> readAll() {
    return ResponseEntity.ok(deliveryService.readAll());
  }

  @PutMapping("/{deliveryId}")
  public ResponseEntity<DeliveryUpdateResponseDto> update(
      @PathVariable UUID deliveryId,
      @Valid @RequestBody DeliveryUpdateRequestDto requestDto
  ) {
    return ResponseEntity.ok(deliveryService.update(deliveryId, requestDto));
  }

  @DeleteMapping("/{deliveryId}")
  public ResponseEntity<Void> delete(@PathVariable UUID deliveryId) {
    deliveryService.delete(deliveryId);
    return ResponseEntity.ok().build();
  }
}
